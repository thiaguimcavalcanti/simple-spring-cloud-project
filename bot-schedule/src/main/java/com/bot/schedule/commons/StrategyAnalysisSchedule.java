package com.bot.schedule.commons;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.client.StrategyClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class StrategyAnalysisSchedule {

    protected ExchangeEnum exchangeEnum;

    private final StrategyClient strategiesClient;

    public StrategyAnalysisSchedule(StrategyClient strategiesClient) {
        this.strategiesClient = strategiesClient;
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void monitoringStrategiesSuccessTask() {
        strategiesClient.monitoringStrategies(exchangeEnum);
    }
}
