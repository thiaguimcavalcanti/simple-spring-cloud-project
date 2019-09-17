package com.bot.exchanges.commons;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.dto.OrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;

import java.util.List;

public interface ExchangeApiFacade {

    TickerDTO getTicker(ExchangeProduct exchangeProduct);

    List<? extends BalanceDTO> getBalances(String userId);

    List<? extends OrderDTO> getAllOrders(String userId, ExchangeProduct exchangeProduct);

    List<? extends OrderDTO> getOpenOrders(String userId, ExchangeProduct exchangeProduct);

    List<? extends OrderHistoryDTO> getOrderHistory(String userId, ExchangeProduct exchangeProduct);

    List<? extends CandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    CandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    List<? extends ExchangeProductDTO> getExchangeProductList();

    List<? extends MarketSummaryDTO> getMarketSummaries(ExchangeProduct exchangeProduct);

    OrderDTO createNewOrder(OrderHistory orderHistory);

    OrderDTO cancelOrder(String userId, ExchangeProduct exchangeProduct, String orderId);
}
