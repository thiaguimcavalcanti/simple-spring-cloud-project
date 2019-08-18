package com.bot.schedule.commons.service;

import com.bot.exchanges.commons.enums.ExchangeEnum;

public interface CandlestickService {

    void getCandlesticks(ExchangeEnum exchangeEnum);
}
