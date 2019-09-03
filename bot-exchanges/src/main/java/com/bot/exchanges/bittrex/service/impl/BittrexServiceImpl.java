package com.bot.exchanges.bittrex.service.impl;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.bittrex.BittrexProperties;
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
    private final BittrexProperties bittrexProperties;

    @Autowired
    public BittrexServiceImpl(BittrexAccountClient bittrexAccountClient,
                              BittrexPublicClient bittrexPublicClient,
                              BittrexMarketClient bittrexMarketClient,
                              BittrexPublic2Client bittrexPublic2Client,
                              BittrexProperties bittrexProperties)  {
        super.exchangeEnum = ExchangeEnum.BITTREX;
        this.bittrexAccountClient = bittrexAccountClient;
        this.bittrexPublicClient = bittrexPublicClient;
        this.bittrexMarketClient = bittrexMarketClient;
        this.bittrexPublic2Client = bittrexPublic2Client;
        this.bittrexProperties = bittrexProperties;
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
        List<BittrexCandlestickDTO> candlesticks = bittrexPublic2Client.getCandlesticks(bittrexProperties
                .getPeriodByPeriodEnum(periodEnum), getSymbol(exchangeProduct)).getResult();

        candlesticks.forEach(candlestickDTO -> {
            candlestickDTO.setBeginTime(candlestickDTO.getEndTime());
            candlestickDTO.setEndTime(candlestickDTO.getEndTime().plus(periodEnum.getDuration()));
        });

        return candlesticks;
    }

    @Override
    public BittrexCandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        List<BittrexCandlestickDTO> candlesticks = bittrexPublic2Client.getCandlesticks(bittrexProperties
                .getPeriodByPeriodEnum(periodEnum), getSymbol(exchangeProduct)).getResult();

        if (CollectionUtils.isNotEmpty(candlesticks) && candlesticks.size() >= 2) {
            BittrexCandlestickDTO candlestickDTO = candlesticks.get(candlesticks.size() - 2);
            candlestickDTO.setBeginTime(candlestickDTO.getEndTime());
            candlestickDTO.setEndTime(candlestickDTO.getEndTime().plus(periodEnum.getDuration()));
            return candlestickDTO;
        }

        return null;
    }

    @Override
    public List<BittrexExchangeProductDTO> getExchangeProductList() {
        return bittrexPublicClient.getMarkets().getResult();
    }

    private String getSymbol(ExchangeProduct exchangeProduct) {
        return exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId();
    }
}
