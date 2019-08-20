package com.bot.exchanges.binance.service.impl;

import com.bot.exchanges.binance.client.BinancePublicClient;
import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceExchangeProductDTO;
import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.service.impl.ExchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinanceServiceImpl extends ExchangeServiceImpl implements BinanceService {

    @Autowired
    private BinancePublicClient binancePublicClient;

    public BinanceServiceImpl()  {
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }

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
    public List<BinanceCandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        String symbol = exchangeProduct.getBaseProductId() + exchangeProduct.getProductId();
        return binancePublicClient.getCandlesticks(symbol, "5m", null, null, null);
    }

    @Override
    public List<BinanceExchangeProductDTO> getExchangeProductList() {
        return binancePublicClient.getExchangeInfo().getSymbols();
    }
}
