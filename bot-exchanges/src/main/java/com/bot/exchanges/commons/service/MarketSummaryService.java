package com.bot.exchanges.commons.service;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface MarketSummaryService {

    void refreshMarketSummaries(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct);
}
