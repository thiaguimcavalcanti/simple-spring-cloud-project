package com.bot.exchanges.bittrex;

import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.dto.OrderDTO;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.bittrex.client.BittrexAccountClient;
import com.bot.exchanges.bittrex.client.BittrexMarketClient;
import com.bot.exchanges.bittrex.client.BittrexPublic2Client;
import com.bot.exchanges.bittrex.client.BittrexPublicClient;
import com.bot.exchanges.bittrex.dto.account.BittrexBalanceDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexOrderHistoryDTO;
import com.bot.exchanges.bittrex.dto.market.BittrexOrderDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexCandlestickDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexExchangeProductDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexTickerDTO;
import com.bot.exchanges.commons.CommonExchangeProperties;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.ExchangeApiFacade;
import com.bot.exchanges.commons.entities.OrderHistory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BittrexApiFacade implements ExchangeApiFacade {

    private final BittrexAccountClient bittrexAccountClient;
    private final BittrexPublicClient bittrexPublicClient;
    private final BittrexMarketClient bittrexMarketClient;
    private final BittrexPublic2Client bittrexPublic2Client;
    private final CommonExchangeProperties bittrexProperties;

    @Autowired
    public BittrexApiFacade(BittrexAccountClient bittrexAccountClient,
                            BittrexPublicClient bittrexPublicClient,
                            BittrexMarketClient bittrexMarketClient,
                            BittrexPublic2Client bittrexPublic2Client,
                            CommonExchangeProperties bittrexProperties)  {
        this.bittrexAccountClient = bittrexAccountClient;
        this.bittrexPublicClient = bittrexPublicClient;
        this.bittrexMarketClient = bittrexMarketClient;
        this.bittrexPublic2Client = bittrexPublic2Client;
        this.bittrexProperties = bittrexProperties;
    }

    @Override
    public BittrexTickerDTO getTicker(ExchangeProduct exchangeProduct) {
        return bittrexPublicClient.ticker(getSymbol(exchangeProduct)).getResult();
    }

    @Override
    public List<BittrexBalanceDTO> getBalances(String userId) {
        return bittrexAccountClient.getBalances(userId).getResult();
    }

    @Override
    public List<? extends OrderDTO> getAllOrders(String userId, ExchangeProduct exchangeProduct) {
        return null;
    }

    @Override
    public List<BittrexOrderDTO> getOpenOrders(String userId, ExchangeProduct exchangeProduct) {
        return bittrexMarketClient.getOpenOrders(userId, getSymbol(exchangeProduct)).getResult();
    }

    @Override
    public List<BittrexOrderHistoryDTO> getOrderHistory(String userId, ExchangeProduct exchangeProduct) {
        return bittrexAccountClient.getOrderHistory(userId, getSymbol(exchangeProduct)).getResult();
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

    @Override
    public List<? extends MarketSummaryDTO> getMarketSummaries(ExchangeProduct exchangeProduct) {
        return null;
    }

    @Override
    public OrderDTO createNewOrder(OrderHistory orderHistory) {
        return null;
    }

    @Override
    public OrderDTO cancelOrder(String userId, ExchangeProduct exchangeProduct, String orderId) {
        return null;
    }

    private String getSymbol(ExchangeProduct exchangeProduct) {
        return exchangeProduct != null ? exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId() : null;
    }
}
