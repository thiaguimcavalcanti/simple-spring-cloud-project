package com.bot.schedule.commons.service;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;

public interface CandlestickService {

    void refreshLatestCandlesticks(ExchangeEnum exchangeEnum, PeriodEnum periodEnum);
}
