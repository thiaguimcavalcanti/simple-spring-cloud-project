package com.bot.exchanges.binance;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.OrderDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.OrderStatusEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.binance.client.BinanceAccountClient;
import com.bot.exchanges.binance.client.BinancePublicClient;
import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceExchangeProductDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceMarketSummaryDTO;
import com.bot.exchanges.binance.dto.account.BinanceOrderHistoryDTO;
import com.bot.exchanges.commons.CommonExchangeProperties;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.ExchangeApiFacade;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.repository.TickerRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bot.exchanges.binance.enums.BinanceOrderTypeEnum.LIMIT_MAKER;

@Service
public class BinanceApiFacade implements ExchangeApiFacade {

    private final BinancePublicClient binancePublicClient;
    private final BinanceAccountClient binanceAccountClient;
    private final TickerRepository tickerRepository;
    private final ModelMapper mapper;
    private final CommonExchangeProperties binanceProperties;

    @Autowired
    public BinanceApiFacade(BinancePublicClient binancePublicClient,
                            BinanceAccountClient binanceAccountClient,
                            TickerRepository tickerRepository,
                            ModelMapper mapper,
                            CommonExchangeProperties binanceProperties)  {
        this.binancePublicClient = binancePublicClient;
        this.binanceAccountClient = binanceAccountClient;
        this.tickerRepository = tickerRepository;
        this.mapper = mapper;
        this.binanceProperties = binanceProperties;
    }

    @Override
    public TickerDTO getTicker(ExchangeProduct exchangeProduct) {
        return mapper.map(tickerRepository.findByExchangeProductId(exchangeProduct.getId()), TickerDTO.class);
    }

    @Override
    public List<? extends BalanceDTO> getBalances(String userId) {
        return null;
    }

    @Override
    public List<? extends OrderDTO> getAllOrders(String userId, ExchangeProduct exchangeProduct) {
        return binanceAccountClient.getAllOrders(userId, getSymbol(exchangeProduct), 50000,
                System.currentTimeMillis());
    }

    @Override
    public List<? extends OrderDTO> getOpenOrders(String userId, ExchangeProduct exchangeProduct) {
        return binanceAccountClient.getOpenOrders(userId, getSymbol(exchangeProduct), 50000,
                System.currentTimeMillis());
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
    public List<BinanceMarketSummaryDTO> getMarketSummaries(ExchangeProduct exchangeProduct) {
        return binancePublicClient.getMarketSummaries(getSymbol(exchangeProduct));
    }

    @Override
    public OrderDTO createNewOrder(OrderHistory orderHistory) {
        /*return binanceAccountClient.createNewOrder(orderHistory.getUserExchange().getUserId(),
                getSymbol(orderHistory.getExchangeProduct()), orderHistory.getType(), LIMIT_MAKER,
                orderHistory.getQuantity(), orderHistory.getPrice(), 50000, System.currentTimeMillis());*/
        return createNewOrderTest(orderHistory);
    }

    private OrderDTO createNewOrderTest(OrderHistory orderHistory) {
        OrderDTO newOrderTest = binanceAccountClient.createNewOrderTest(orderHistory.getUserExchange().getUserId(),
                getSymbol(orderHistory.getExchangeProduct()), orderHistory.getType(), LIMIT_MAKER,
                orderHistory.getQuantity(), orderHistory.getPrice(), 50000, System.currentTimeMillis());
        newOrderTest.setId(((int)(Math.random() * 20 + 1)) + "");
        newOrderTest.setStatus(OrderStatusEnum.FILLED);
        newOrderTest.setPrice(orderHistory.getPrice());
        newOrderTest.setQuantity(orderHistory.getQuantity());
        newOrderTest.setDate(orderHistory.getDate());
        return newOrderTest;
    }

    @Override
    public OrderDTO cancelOrder(String userId, ExchangeProduct exchangeProduct, String orderId) {
        /*return binanceAccountClient.cancelOrder(userId, getSymbol(exchangeProduct), orderId, 50000,
                System.currentTimeMillis());*/
        OrderDTO newOrderTest = new OrderDTO();
        newOrderTest.setStatus(OrderStatusEnum.CANCELED);
        return newOrderTest;
    }

    private String getSymbol(ExchangeProduct exchangeProduct) {
        return exchangeProduct != null ?  exchangeProduct.getProductId() + exchangeProduct.getBaseProductId() : null;
    }
}
