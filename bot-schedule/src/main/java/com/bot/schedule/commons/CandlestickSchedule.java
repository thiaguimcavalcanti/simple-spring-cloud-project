package com.bot.schedule.commons;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class CandlestickSchedule {

    protected ExchangeEnum exchangeEnum;

    @Autowired
    private CandlestickService candlestickService;

    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void loadAndSaveLatestTickFiveMinsTask() {
        candlestickService.refreshLatestTicks(exchangeEnum, PeriodEnum.FIVE_MIN);
    }

    @Async
    @Scheduled(cron = "*/2 * * * * ?")
    public void loadAndSaveLatestTickFifteenMinsTask() {
        candlestickService.refreshLatestTicks(exchangeEnum, PeriodEnum.FIFTEEN_MIN);
    }

    @Async
    @Scheduled(cron = "*/3 * * * * ?")
    public void loadAndSaveLatestTickOneHourTask() {
        candlestickService.refreshLatestTicks(exchangeEnum, PeriodEnum.ONE_HOUR);
    }

    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void loadAndSaveCandlesFiveMinsTask() {
        candlestickService.refreshCandlesticks(exchangeEnum, PeriodEnum.FIVE_MIN);
    }

    @Async
    @Scheduled(cron = "*/2 * * * * ?")
    public void loadAndSaveCandlesFifteenMinsTask() {
        candlestickService.refreshCandlesticks(exchangeEnum, PeriodEnum.FIFTEEN_MIN);
    }

    @Async
    @Scheduled(cron = "*/3 * * * * ?")
    public void loadAndSaveCandlesOneHourTask() {
        candlestickService.refreshCandlesticks(exchangeEnum, PeriodEnum.ONE_HOUR);
    }
}
