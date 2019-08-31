package com.bot.exchanges.trade.service;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;

public interface StrategyAnalysisService {

    void monitoringStrategiesSuccess(ExchangeEnum exchangeEnum);

    void analyzeStrategies(PeriodEnum periodEnum, ExchangeProduct exchangeProduct);
}
