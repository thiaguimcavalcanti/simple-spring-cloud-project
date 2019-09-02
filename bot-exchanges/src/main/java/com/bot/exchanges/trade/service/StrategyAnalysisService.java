package com.bot.exchanges.trade.service;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;

public interface StrategyAnalysisService {

    void monitoringStrategies(ExchangeEnum exchangeEnum);

    void analyzeStrategies(ExchangeEnum exchangeEnum, String baseProductId, String productId, PeriodEnum periodEnum);
}
