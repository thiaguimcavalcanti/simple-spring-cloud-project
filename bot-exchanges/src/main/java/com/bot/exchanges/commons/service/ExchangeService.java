package com.bot.exchanges.commons.service;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.dto.OpenOrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;

import java.util.List;

public interface ExchangeService {

    TickerDTO getTicker(String market);

    List<? extends BalanceDTO> getBalances(String userId);

    List<? extends OpenOrderDTO> getOpenOrders(String userId, String market);

    List<? extends OrderHistoryDTO> getOrderHistory(String userId, String market);

    List<? extends CandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    CandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    List<Candlestick> refreshCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    Candlestick refreshLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    void refreshExchangeProductList();

    List<? extends ExchangeProductDTO> getExchangeProductList();
}
