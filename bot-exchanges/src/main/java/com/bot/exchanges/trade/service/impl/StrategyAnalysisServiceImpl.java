package com.bot.exchanges.trade.service.impl;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.OrderStatusEnum;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.Strategy;
import com.bot.exchanges.commons.entities.StrategyRule;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.StrategyRepository;
import com.bot.exchanges.commons.repository.StrategyRuleRepository;
import com.bot.exchanges.commons.service.OrderHistoryService;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import com.bot.exchanges.trade.strategies.StrategyCompiler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Rule;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bot.commons.enums.OrderTypeEnum.BUY;
import static org.apache.commons.lang.StringUtils.EMPTY;

@Service
@Transactional
public class StrategyAnalysisServiceImpl implements StrategyAnalysisService {

    private static final Logger LOG = LogManager.getLogger(StrategyAnalysisServiceImpl.class.getName());

    private StrategyRepository strategyRepository;
    private StrategyRuleRepository strategyRuleRepository;
    private CandlestickRepository candlestickRepository;
    private ExchangeProductRepository exchangeProductRepository;

    private OrderHistoryService orderHistoryService;

    @Autowired
    public StrategyAnalysisServiceImpl(StrategyRepository strategyRepository,
                                       StrategyRuleRepository strategyRuleRepository,
                                       CandlestickRepository candlestickRepository,
                                       OrderHistoryService orderHistoryService,
                                       ExchangeProductRepository exchangeProductRepository) {
        this.strategyRepository = strategyRepository;
        this.strategyRuleRepository = strategyRuleRepository;
        this.candlestickRepository = candlestickRepository;
        this.orderHistoryService = orderHistoryService;
        this.exchangeProductRepository = exchangeProductRepository;
    }

    private void monitoringStrategy(Strategy strategyToSearch, Bar currentBar) {
        Optional<Strategy> strategyOptional = strategyRepository.findById(strategyToSearch.getId());

        strategyOptional.ifPresent(strategy -> {
            boolean buySatisfied = CollectionUtils.isNotEmpty(strategy.getStrategyRules());
            boolean sellSatisfied = buySatisfied;

            for (StrategyRule rule : strategy.getStrategyRules()) {
                if (rule.getBuyActive()) {
                    buySatisfied &= BooleanUtils.isTrue(rule.getBuySatisfied());
                }
                if (rule.getSellActive()) {
                    sellSatisfied &= BooleanUtils.isTrue(rule.getSellSatisfied());
                }
            }

            // Create new open order to start the trade
            Optional<OrderHistory> orderHistory = orderHistoryService.save(buySatisfied, sellSatisfied,
                    strategy.getExchangeProduct(), strategy.getUserExchange(), currentBar.getEndTime(),
                    (CustomBigDecimal) currentBar.getClosePrice());

            /*orderHistory.ifPresent(oh -> {
                orderHistoryService.confirmBuySellExecutedWithSuccess(oh);
                LOG.warn("Order executed - " + oh.getType());
            }); // ONLY TO TEST*/
        });
    }

    @Override
    @Async
    public void analyzeStrategies(ExchangeProduct exchangeProduct, ZonedDateTime time, PeriodEnum periodEnum) {
        BaseTimeSeries series = createTimeSeries(periodEnum, exchangeProduct, time);
        Bar currentBar = series.getBar(series.getEndIndex());

        LOG.warn("analyzeStrategies: " + periodEnum + ": " + exchangeProduct.getBaseProductId() +
                "-" + exchangeProduct.getProductId() + " - " + currentBar.getBeginTime());

        List<StrategyRule> strategyRules = strategyRuleRepository.findByExchangeProductAndPeriodEnum(exchangeProduct, periodEnum);
        if (CollectionUtils.isNotEmpty(strategyRules)) {
            for (StrategyRule strategyRule : strategyRules) {
                String buy = strategyRule.getBuyActive() ? strategyRule.getBuy() : EMPTY;
                String sell = strategyRule.getSellActive() ? strategyRule.getSell() : EMPTY;

                Rule buyRule = StrategyCompiler.translateJsonToRule(buy, series);
                Rule sellRule = StrategyCompiler.translateJsonToRule(sell, series);
                org.ta4j.core.Strategy strategy = new BaseStrategy(buyRule, sellRule);

                strategyRule.setBuySatisfied(strategy.shouldEnter(series.getEndIndex()));
                strategyRule.setSellSatisfied(strategy.shouldExit(series.getEndIndex()));

                strategyRuleRepository.save(strategyRule);
            }

            // Monitoring current strategy
            Strategy strategy = strategyRules.get(0).getStrategy();
            monitoringStrategy(strategy, currentBar);
        }
    }

    private BaseTimeSeries createTimeSeries(PeriodEnum periodEnum, ExchangeProduct exchangeProduct, ZonedDateTime time) {
        Duration ticks = ((Duration) periodEnum.getDuration()).multipliedBy(200);
        ZonedDateTime beginTime = time.minusMinutes(ticks.toMinutes());

        // Get ticks to analyze
        List<Candlestick> candlesticks = candlestickRepository.getToAnalyze(exchangeProduct, beginTime, time, periodEnum);

        return new BaseTimeSeries(new ArrayList<>(candlesticks));
    }
}
