package com.bot.exchanges.trade.service;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;

import java.time.ZonedDateTime;

public interface StrategyAnalysisService {
    void analyzeStrategies(ExchangeProduct exchangeProduct, ZonedDateTime beginTime, PeriodEnum periodEnum);
}
