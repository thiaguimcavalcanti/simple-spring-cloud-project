package com.bot.exchanges.alphavantage;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.dto.OrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.alphavantage.client.AlphaVantagePublicClient;
import com.bot.exchanges.alphavantage.dto.AlphaVantageCandlestickDTO;
import com.bot.exchanges.alphavantage.enums.AlphaVantageSymbolEnum;
import com.bot.exchanges.alphavantage.enums.FunctionTypeEnum;
import com.bot.exchanges.commons.CommonExchangeProperties;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.ExchangeApiFacade;
import com.bot.exchanges.commons.entities.OrderHistory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlphaVantageApiFacade implements ExchangeApiFacade {

    private final AlphaVantagePublicClient alphaVantagePublicClient;
    private final CommonExchangeProperties alphaVantageProperties;

    @Autowired
    public AlphaVantageApiFacade(AlphaVantagePublicClient alphaVantagePublicClient,
                                 CommonExchangeProperties alphaVantageProperties) {
        this.alphaVantagePublicClient = alphaVantagePublicClient;
        this.alphaVantageProperties = alphaVantageProperties;
    }

    @Override
    public TickerDTO getTicker(ExchangeProduct market) {
        return null;
    }

    @Override
    public List<? extends BalanceDTO> getBalances(String userId) {
        return null;
    }

    @Override
    public List<? extends OrderDTO> getAllOrders(String userId, ExchangeProduct exchangeProduct) {
        return null;
    }

    @Override
    public List<? extends OrderDTO> getOpenOrders(String userId, ExchangeProduct exchangeProduct) {
        return null;
    }

    @Override
    public List<? extends OrderHistoryDTO> getOrderHistory(String userId, ExchangeProduct exchangeProduct) {
        return null;
    }

    @Override
    public List<AlphaVantageCandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        List<AlphaVantageCandlestickDTO> candlesticks = alphaVantagePublicClient.getTicks(getSymbol(exchangeProduct),
                FunctionTypeEnum.getByPeriodEnum(periodEnum), alphaVantageProperties.getPeriodByPeriodEnum(periodEnum)).getItems();

        candlesticks.forEach(candlestickDTO -> candlestickDTO.setEndTime(candlestickDTO.getBeginTime().plus(periodEnum.getDuration())));

        return candlesticks;
    }

    @Override
    public AlphaVantageCandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        List<AlphaVantageCandlestickDTO> candlesticks = alphaVantagePublicClient.getTicks(getSymbol(exchangeProduct),
                FunctionTypeEnum.getByPeriodEnum(periodEnum), alphaVantageProperties.getPeriodByPeriodEnum(periodEnum)).getItems();

        if (CollectionUtils.isNotEmpty(candlesticks) && candlesticks.size() >= 1) {
            AlphaVantageCandlestickDTO candlestickDTO = candlesticks.get(1);
            candlestickDTO.setEndTime(candlestickDTO.getBeginTime().plus(periodEnum.getDuration()));
            return candlestickDTO;
        }

        return null;
    }

    @Override
    public List<? extends ExchangeProductDTO> getExchangeProductList() {
        return null;
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
        return exchangeProduct != null ? AlphaVantageSymbolEnum.valueOf(exchangeProduct.getProductId()).getSymbol() : null;
    }
}
