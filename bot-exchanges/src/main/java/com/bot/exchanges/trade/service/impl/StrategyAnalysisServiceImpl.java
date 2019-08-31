package com.bot.exchanges.trade.service.impl;

import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.Strategy;
import com.bot.exchanges.commons.entities.StrategyRule;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.OrderStatusEnum;
import com.bot.exchanges.commons.enums.OrderTypeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.StrategyRepository;
import com.bot.exchanges.commons.repository.StrategyRuleRepository;
import com.bot.exchanges.commons.service.OrderHistoryService;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import com.bot.exchanges.trade.strategies.StrategyCompiler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Rule;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StrategyAnalysisServiceImpl implements StrategyAnalysisService {

    private static final Logger LOG = LogManager.getLogger(StrategyAnalysisServiceImpl.class.getName());

    private StrategyRepository strategyRepository;
    private StrategyRuleRepository strategyRuleRepository;
    private CandlestickRepository candlestickRepository;
    private OrderHistoryService orderHistoryService;

    @Autowired
    public StrategyAnalysisServiceImpl(StrategyRepository strategyRepository,
                                       StrategyRuleRepository strategyRuleRepository,
                                       CandlestickRepository candlestickRepository,
                                       OrderHistoryService orderHistoryService) {
        this.strategyRepository = strategyRepository;
        this.strategyRuleRepository = strategyRuleRepository;
        this.candlestickRepository = candlestickRepository;
        this.orderHistoryService = orderHistoryService;
    }

    @Override
    public void monitoringStrategiesSuccess(ExchangeEnum exchangeEnum) {
        List<Strategy> strategies = strategyRepository.findByActiveIsTrueAndExchangeId(exchangeEnum.getId()); // change to find by exchange enum

        for (Strategy strategy : strategies) {
            boolean buySatisfied = CollectionUtils.isNotEmpty(strategy.getStrategyRules());
            boolean sellSatisfied = buySatisfied;

            for (StrategyRule rule : strategy.getStrategyRules()) {
                buySatisfied &= BooleanUtils.isTrue(rule.getBuySatisfied());
                sellSatisfied &= BooleanUtils.isTrue(rule.getSellSatisfied());
            }

            strategy.setBuySatisfied(buySatisfied);
            strategy.setSellSatisfied(sellSatisfied);
            strategyRepository.save(strategy);

            // Create new open order to start the trade
            createNewOrderBasedOnStrategy(exchangeEnum, strategy);

            LOG.debug("Strategy - Buy: " + strategy.getBuySatisfied() + " - Sell: " + strategy.getSellSatisfied());
        }
    }

    private void createNewOrderBasedOnStrategy(ExchangeEnum exchangeEnum, Strategy strategy) {
        Boolean buySatisfied = BooleanUtils.isTrue(strategy.getBuySatisfied());
        Boolean sellSatisfied = BooleanUtils.isTrue(strategy.getSellSatisfied());

        if (buySatisfied || sellSatisfied) {
            OrderHistory latestOrderHistory = orderHistoryService.findTopByExchangeProductIdAndUserExchangeId(strategy.getExchangeProductId(),
                    strategy.getUserExchangeId());

            if (buySatisfied && (latestOrderHistory == null
                    || (OrderTypeEnum.SELL.equals(latestOrderHistory.getType()) && OrderStatusEnum.CLOSED.equals(latestOrderHistory.getStatus())))) {
                orderHistoryService.save(strategy.getExchangeProduct(), strategy.getUserExchange(), OrderTypeEnum.BUY);
            } else if (sellSatisfied && (latestOrderHistory != null && OrderTypeEnum.BUY.equals(latestOrderHistory.getType())
                    && OrderStatusEnum.CLOSED.equals(latestOrderHistory.getStatus()))) {
                orderHistoryService.save(strategy.getExchangeProduct(), strategy.getUserExchange(), OrderTypeEnum.SELL);
            }
        }
    }

    @Override
    public void analyzeStrategies(PeriodEnum periodEnum, ExchangeProduct exchangeProduct) {
        BaseTimeSeries series = createTimeSeries(periodEnum, exchangeProduct);

        List<StrategyRule> strategyRules = strategyRuleRepository.findByExchangeProductAndPeriodEnum(exchangeProduct, periodEnum);

        for (StrategyRule strategyRule : strategyRules) {
            Rule buyRule = StrategyCompiler.translateJsonToRule(strategyRule.getBuy(), series);
            Rule sellRule = StrategyCompiler.translateJsonToRule(strategyRule.getSell(), series);

            org.ta4j.core.Strategy strategy = new BaseStrategy(buyRule, sellRule);
            strategyRule.setBuySatisfied(strategy.shouldEnter(series.getEndIndex()));
            strategyRule.setSellSatisfied(strategy.shouldExit(series.getEndIndex()));

            strategyRuleRepository.save(strategyRule);
        }
    }

    private BaseTimeSeries createTimeSeries(PeriodEnum periodEnum, ExchangeProduct exchangeProduct) {
        Duration ticks = ((Duration) periodEnum.getDuration()).multipliedBy(200);
        ZonedDateTime beginTime = ZonedDateTime.now().minusMinutes(ticks.toMinutes());

        // Get ticks to analyze
        List<Candlestick> candlesticks = candlestickRepository.getToAnalyze(exchangeProduct, beginTime, null, periodEnum);

        return new BaseTimeSeries(new ArrayList<>(candlesticks));
    }
}
