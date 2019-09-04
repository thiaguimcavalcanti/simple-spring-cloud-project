package com.bot.exchanges.trade.service;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;

public interface StrategyAnalysisService {

    void monitoringStrategies(ExchangeEnum exchangeEnum);

    void analyzeStrategies(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);
}
