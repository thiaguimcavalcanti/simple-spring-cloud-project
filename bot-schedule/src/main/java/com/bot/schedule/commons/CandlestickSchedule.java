package com.bot.schedule.commons;

import com.bot.exchanges.commons.enums.ExchangeEnum;
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
    public void getCandlesticks() {
        candlestickService.getCandlesticks(exchangeEnum);
    }

}
