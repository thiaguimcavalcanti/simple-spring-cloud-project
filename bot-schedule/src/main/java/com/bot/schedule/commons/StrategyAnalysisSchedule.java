package com.bot.schedule.commons;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public abstract class StrategyAnalysisSchedule {

    protected ExchangeEnum exchangeEnum;

    private final ExchangeProductRepository exchangeProductRepository;

    private final StrategyAnalysisService strategyAnalysisService;

    public StrategyAnalysisSchedule(StrategyAnalysisService strategyAnalysisService,
                                    ExchangeProductRepository exchangeProductRepository) {
        this.strategyAnalysisService = strategyAnalysisService;
        this.exchangeProductRepository = exchangeProductRepository;
    }

    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void analyzeStrategyRulesTask() {
        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeEnum.getId());
        if (CollectionUtils.isNotEmpty(exchangeProducts)) {
            strategyAnalysisService.analyzeStrategies(PeriodEnum.FIVE_MIN, exchangeProducts.get(0));
        }
    }

    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void monitoringStrategiesSuccessTask() {
        strategyAnalysisService.monitoringStrategiesSuccess(exchangeEnum);
    }
}
