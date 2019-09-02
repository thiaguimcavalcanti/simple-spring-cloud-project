package com.bot.schedule.commons.service;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;

public interface CandlestickService {

    void refreshLatestCandlestick(ExchangeEnum exchangeEnum, PeriodEnum periodEnum);
}
