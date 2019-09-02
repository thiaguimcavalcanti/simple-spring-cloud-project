package com.bot.exchanges.binance.service.impl;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.OpenOrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.binance.client.BinancePublicClient;
import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceExchangeProductDTO;
import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.service.impl.ExchangeServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinanceServiceImpl extends ExchangeServiceImpl implements BinanceService {

    private final BinancePublicClient binancePublicClient;

    @Autowired
    public BinanceServiceImpl(BinancePublicClient binancePublicClient)  {
        super.exchangeEnum = ExchangeEnum.BINANCE;
        this.binancePublicClient = binancePublicClient;
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
    public CandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        String symbol = exchangeProduct.getBaseProductId() + exchangeProduct.getProductId();
        List<BinanceCandlestickDTO> candlesticks = binancePublicClient.getCandlesticks(symbol, "5m", null, null, 2);
        return CollectionUtils.isNotEmpty(candlesticks) ? candlesticks.get(0) : null;
    }

    @Override
    public List<BinanceExchangeProductDTO> getExchangeProductList() {
        return binancePublicClient.getExchangeInfo().getSymbols();
    }
}
