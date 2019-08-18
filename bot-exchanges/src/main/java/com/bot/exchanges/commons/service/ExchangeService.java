package com.bot.exchanges.commons.service;

import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.bot.exchanges.commons.dto.TickerDTO;

import java.util.List;

public interface ExchangeService {

    TickerDTO getTicker(String market);

    List<? extends BalanceDTO> getBalances(String userId);

    List<? extends OpenOrderDTO> getOpenOrders(String userId, String market);

    List<? extends OrderHistoryDTO> getOrderHistory(String userId, String market);

    List<? extends BinanceCandlestickDTO> getCandlesticks(String market, String interval);
}
