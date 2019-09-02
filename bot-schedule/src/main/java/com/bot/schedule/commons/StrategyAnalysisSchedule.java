package com.bot.schedule.commons;

import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.schedule.commons.client.StrategyClient;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.ServletContext;

public abstract class StrategyAnalysisSchedule {

    protected ExchangeEnum exchangeEnum;

    private ServletContext servletContext;

    private final StrategyClient strategiesClient;

    public StrategyAnalysisSchedule(StrategyClient strategiesClient,
                                    ServletContext servletContext) {
        this.strategiesClient = strategiesClient;
        this.servletContext = servletContext;
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void analyzeStrategyRulesTask() {
        ExchangeSessionHelper exchangeSessionHelper = ExchangeSessionHelper.getInstance(servletContext);
        Pair<PeriodEnum, ExchangeProductDTO> dataToAnalyze = exchangeSessionHelper.getFirstExchangeProductToStrategyAnalysis(exchangeEnum);

        if (dataToAnalyze != null) {
            ExchangeProductDTO exchangeProduct = dataToAnalyze.getRight();
            strategiesClient.analyzeStrategies(exchangeEnum, exchangeProduct.getBaseProductId(),
                    exchangeProduct.getProductId(), dataToAnalyze.getLeft());
            exchangeSessionHelper.removeExchangeProductToStrategyAnalysis(exchangeEnum, dataToAnalyze);
        }
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void monitoringStrategiesSuccessTask() {
        strategiesClient.monitoringStrategies(exchangeEnum);
    }
}
