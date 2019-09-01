package com.bot.schedule.commons;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class CandlestickSchedule {

    protected ExchangeEnum exchangeEnum;

    private CandlestickService candlestickService;

    public CandlestickSchedule(CandlestickService candlestickService) {
        this.candlestickService = candlestickService;
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void refreshLatestCandlesticksFiveMinsTask() {
        candlestickService.refreshLatestCandlestick(exchangeEnum, PeriodEnum.FIVE_MIN);
    }

    @Async
    @Scheduled(cron = "*/2 * * ? * *")
    public void refreshLatestCandlesticksFifteenMinsTask() {
        candlestickService.refreshLatestCandlestick(exchangeEnum, PeriodEnum.FIFTEEN_MIN);
    }

    @Async
    @Scheduled(cron = "*/3 * * ? * *")
    public void refreshLatestCandlesticksOneHourTask() {
        candlestickService.refreshLatestCandlestick(exchangeEnum, PeriodEnum.ONE_HOUR);
    }
}
