package com.bot.schedule.commons.service.impl;

import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandlesticksServiceImpl implements CandlestickService {

    @Autowired
    private ExchangesApiFacade exchangesApiFacade;

    @Override
    public void getCandlesticks(ExchangeEnum exchangeEnum) {
        System.out.println(exchangeEnum.name());
        exchangesApiFacade.getCandlesticks(exchangeEnum, "ETHBTC", "5m");
    }
}
