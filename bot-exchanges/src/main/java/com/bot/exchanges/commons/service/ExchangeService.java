package com.bot.exchanges.commons.service;

import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.dto.ExchangeProductDTO;
import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.PeriodEnum;

import java.util.List;

public interface ExchangeService {

    TickerDTO getTicker(String market);

    List<? extends BalanceDTO> getBalances(String userId);

    List<? extends OpenOrderDTO> getOpenOrders(String userId, String market);

    List<? extends OrderHistoryDTO> getOrderHistory(String userId, String market);

    List<? extends CandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    List<Candlestick> refreshCandlesTicks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    void refreshExchangeProductList();

    List<? extends ExchangeProductDTO> getExchangeProductList();
}
