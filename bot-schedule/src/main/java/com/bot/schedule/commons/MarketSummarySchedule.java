package com.bot.schedule.commons;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.client.MarketSummaryClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class MarketSummarySchedule {

    protected ExchangeEnum exchangeEnum;

    private MarketSummaryClient marketSummaryClient;

    public MarketSummarySchedule(MarketSummaryClient marketSummaryClient) {
        this.marketSummaryClient = marketSummaryClient;
    }

    @Async
    @Scheduled(cron = "0 */5 * ? * *")
    public void refreshAllTask() {
        marketSummaryClient.refreshAll(exchangeEnum);
    }
}
