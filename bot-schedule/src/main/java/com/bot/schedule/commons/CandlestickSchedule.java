package com.bot.schedule.commons;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class CandlestickSchedule {

    protected ExchangeEnum exchangeEnum;

    @Autowired
    private CandlestickService candlestickService;

    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void refreshLatestCandlesticksFiveMinsTask() {
        candlestickService.refreshLatestCandlesticks(exchangeEnum, PeriodEnum.FIVE_MIN);
    }

    @Async
    @Scheduled(cron = "*/2 * * * * ?")
    public void refreshLatestCandlesticksFifteenMinsTask() {
        candlestickService.refreshLatestCandlesticks(exchangeEnum, PeriodEnum.FIFTEEN_MIN);
    }

    @Async
    @Scheduled(cron = "*/3 * * * * ?")
    public void refreshLatestCandlesticksOneHourTask() {
        candlestickService.refreshLatestCandlesticks(exchangeEnum, PeriodEnum.ONE_HOUR);
    }
}
