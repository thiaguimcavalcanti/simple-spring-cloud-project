package com.bot.exchanges.commons.service;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.dto.OpenOrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;

import java.util.List;

public interface ExchangeService {

    TickerDTO getTicker(String market);

    List<? extends BalanceDTO> getBalances(String userId);

    List<? extends OpenOrderDTO> getOpenOrders(String userId, ExchangeProduct exchangeProduct);

    List<? extends OrderHistoryDTO> getOrderHistory(String userId, ExchangeProduct exchangeProduct);

    List<? extends CandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    CandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    List<? extends CandlestickDTO> refreshCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    void refreshLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    void refreshExchangeProductList();

    List<? extends ExchangeProductDTO> getExchangeProductList();

    public List<? extends MarketSummaryDTO> getMarketSummaries(ExchangeProduct exchangeProduct);

    void refreshMarketSummaries(ExchangeProduct exchangeProduct);
}
