package com.bot.exchanges.commons.service;

import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;

import java.util.List;

public interface MarketSummaryService {

    void refreshMarketSummaries(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct);

    void refreshMarketSummaries(ExchangeEnum exchangeEnum, List<? extends MarketSummaryDTO> marketSummariesDTO);
}
