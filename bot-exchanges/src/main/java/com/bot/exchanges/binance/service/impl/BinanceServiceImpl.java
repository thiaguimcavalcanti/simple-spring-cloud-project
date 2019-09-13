package com.bot.exchanges.binance.service.impl;

import static com.bot.commons.utils.DateUtils.roundZonedDateTime;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.OpenOrderDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.binance.client.BinanceAccountClient;
import com.bot.exchanges.binance.client.BinancePublicClient;
import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceExchangeProductDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceMarketSummarytDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceOrderHistoryDTO;
import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.commons.CommonExchangeProperties;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.service.impl.ExchangeServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinanceServiceImpl extends ExchangeServiceImpl implements BinanceService {

    private final BinancePublicClient binancePublicClient;
    private final BinanceAccountClient binanceAccountClient;
    private final CommonExchangeProperties binanceProperties;

    @Autowired
    public BinanceServiceImpl(BinancePublicClient binancePublicClient,
                              BinanceAccountClient binanceAccountClient,
                              CommonExchangeProperties binanceProperties)  {
        super.exchangeEnum = ExchangeEnum.BINANCE;
        this.binancePublicClient = binancePublicClient;
        this.binanceAccountClient = binanceAccountClient;
        this.binanceProperties = binanceProperties;
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
    public List<? extends OpenOrderDTO> getOpenOrders(String userId, ExchangeProduct exchangeProduct) {
        return null;
    }

    @Override
    public List<BinanceOrderHistoryDTO> getOrderHistory(String userId, ExchangeProduct exchangeProduct) {
        return binanceAccountClient.getMyTrades(userId, getSymbol(exchangeProduct), null, null,
                null, null, 50000, System.currentTimeMillis());
    }

    @Override
    public List<BinanceCandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        return binancePublicClient.getCandlesticks(getSymbol(exchangeProduct),
                binanceProperties.getPeriodByPeriodEnum(periodEnum), null, null, null);
    }

    @Override
    public BinanceCandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        List<BinanceCandlestickDTO> candlesticks = binancePublicClient.getCandlesticks(getSymbol(exchangeProduct),
                binanceProperties.getPeriodByPeriodEnum(periodEnum), null, null, 2);
        return CollectionUtils.isNotEmpty(candlesticks) ? candlesticks.get(0) : null;
    }

    @Override
    public List<BinanceExchangeProductDTO> getExchangeProductList() {
        return binancePublicClient.getExchangeInfo().getSymbols();
    }

    @Override
    public List<BinanceMarketSummarytDTO> getMarketSummaries(ExchangeProduct exchangeProduct) {
        return binancePublicClient.getMarketSummaries(getSymbol(exchangeProduct));
    }

    private String getSymbol(ExchangeProduct exchangeProduct) {
        return exchangeProduct != null ? exchangeProduct.getBaseProductId() + exchangeProduct.getProductId() : null;
    }
}
