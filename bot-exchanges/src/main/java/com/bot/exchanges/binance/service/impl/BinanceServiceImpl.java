package com.bot.exchanges.binance.service.impl;

import com.bot.exchanges.binance.client.BinancePublicClient;
import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinanceServiceImpl implements BinanceService {

    @Autowired
    private BinancePublicClient binancePublicClient;

    @Override
    public TickerDTO getTicker(String market) {
        return null;
    }

    @Override
    public List<? extends BalanceDTO> getBalances(String userId) {
        return null;
    }

    @Override
    public List<? extends OpenOrderDTO> getOpenOrders(String userId, String market) {
        return null;
    }

    @Override
    public List<? extends OrderHistoryDTO> getOrderHistory(String userId, String market) {
        return null;
    }

    @Override
    public List<? extends BinanceCandlestickDTO> getCandlesticks(String symbol, String interval) {
        return binancePublicClient.getCandlesticks(symbol, interval, null, null, null);
    }
}
