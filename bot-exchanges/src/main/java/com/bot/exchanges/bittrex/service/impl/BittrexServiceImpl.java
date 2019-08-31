package com.bot.exchanges.bittrex.service.impl;

import com.bot.exchanges.bittrex.client.BittrexAccountClient;
import com.bot.exchanges.bittrex.client.BittrexMarketClient;
import com.bot.exchanges.bittrex.client.BittrexPublic2Client;
import com.bot.exchanges.bittrex.client.BittrexPublicClient;
import com.bot.exchanges.bittrex.dto.account.BittrexBalanceDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexOrderHistoryDTO;
import com.bot.exchanges.bittrex.dto.market.BittrexOpenOrderDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexCandlestickDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexExchangeProductDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexTickerDTO;
import com.bot.exchanges.bittrex.service.BittrexService;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.service.impl.ExchangeServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BittrexServiceImpl extends ExchangeServiceImpl implements BittrexService {

    private final BittrexAccountClient bittrexAccountClient;
    private final BittrexPublicClient bittrexPublicClient;
    private final BittrexMarketClient bittrexMarketClient;
    private final BittrexPublic2Client bittrexPublic2Client;

    @Autowired
    public BittrexServiceImpl(BittrexAccountClient bittrexAccountClient,
                              BittrexPublicClient bittrexPublicClient,
                              BittrexMarketClient bittrexMarketClient,
                              BittrexPublic2Client bittrexPublic2Client)  {
        super.exchangeEnum = ExchangeEnum.BITTREX;
        this.bittrexAccountClient = bittrexAccountClient;
        this.bittrexPublicClient = bittrexPublicClient;
        this.bittrexMarketClient = bittrexMarketClient;
        this.bittrexPublic2Client = bittrexPublic2Client;
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
    public List<BittrexCandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        String symbol = exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId();
        return bittrexPublic2Client.getCandlesticks(symbol, "fiveMin").getResult();
    }

    @Override
    public BittrexCandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        String symbol = exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId();
        List<BittrexCandlestickDTO> candlesticks = bittrexPublic2Client.getCandlesticks("fiveMin", symbol).getResult();
        if (CollectionUtils.isNotEmpty(candlesticks) && candlesticks.size() >= 2) {
            return candlesticks.get(candlesticks.size() - 2);
        }
        return null;
    }

    @Override
    public List<BittrexExchangeProductDTO> getExchangeProductList() {
        return bittrexPublicClient.getMarkets().getResult();
    }
}
