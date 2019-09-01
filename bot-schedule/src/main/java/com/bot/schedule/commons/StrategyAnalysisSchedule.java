package com.bot.schedule.commons;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.ServletContext;

public abstract class StrategyAnalysisSchedule {

    protected ExchangeEnum exchangeEnum;

    private ServletContext servletContext;

    private final StrategyAnalysisService strategyAnalysisService;

    public StrategyAnalysisSchedule(StrategyAnalysisService strategyAnalysisService,
                                    ServletContext servletContext) {
        this.strategyAnalysisService = strategyAnalysisService;
        this.servletContext = servletContext;
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void analyzeStrategyRulesTask() {
        ExchangeSessionHelper exchangeSessionHelper = ExchangeSessionHelper.getInstance(servletContext);
        Pair<PeriodEnum, ExchangeProduct> dataToAnalyze = exchangeSessionHelper.getFirstExchangeProductToStrategyAnalysis(exchangeEnum);

        if (dataToAnalyze != null) {
            strategyAnalysisService.analyzeStrategies(dataToAnalyze.getLeft(), dataToAnalyze.getRight());
            exchangeSessionHelper.removeExchangeProductToStrategyAnalysis(exchangeEnum, dataToAnalyze);
        }
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void monitoringStrategiesSuccessTask() {
        strategyAnalysisService.monitoringStrategiesSuccess(exchangeEnum);
    }
}
