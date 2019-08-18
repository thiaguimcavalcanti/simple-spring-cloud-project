package com.bot.exchanges.bittrex.service.impl;

import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.bittrex.client.BittrexAccountClient;
import com.bot.exchanges.bittrex.client.BittrexMarketClient;
import com.bot.exchanges.bittrex.client.BittrexPublicClient;
import com.bot.exchanges.bittrex.dto.account.BittrexBalanceDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexOrderHistoryDTO;
import com.bot.exchanges.bittrex.dto.market.BittrexOpenOrderDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexTickerDTO;
import com.bot.exchanges.bittrex.service.BittrexService;
import com.bot.exchanges.commons.dto.ExchangeProductDTO;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.service.impl.ExchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BittrexServiceImpl extends ExchangeServiceImpl implements BittrexService {

    @Autowired
    private BittrexAccountClient bittrexAccountClient;

    @Autowired
    private BittrexPublicClient bittrexPublicClient;

    @Autowired
    private BittrexMarketClient bittrexMarketClient;

    public BittrexServiceImpl()  {
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }

    @Override
    public BittrexTickerDTO getTicker(String market) {
        return bittrexPublicClient.ticker(market).getResult();
    }

    @Override
    public List<BittrexBalanceDTO> getBalances(String userId) {
        return bittrexAccountClient.getBalances(userId).getResult();
    }

    @Override
    public List<BittrexOpenOrderDTO> getOpenOrders(String userId, String market) {
        return bittrexMarketClient.getOpenOrders(userId, market).getResult();
    }

    @Override
    public List<BittrexOrderHistoryDTO> getOrderHistory(String userId, String market) {
        return bittrexAccountClient.getOrderHistory(userId, market).getResult();
    }

    @Override
    public List<? extends BinanceCandlestickDTO> getCandlesticks(String market, String interval) {
        return null;
    }

    @Override
    public List<? extends ExchangeProductDTO> getExchangeProductList() {
        return bittrexPublicClient.getMarkets().getResult();
    }
}
