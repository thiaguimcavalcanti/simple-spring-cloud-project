package com.bot.exchanges.commons.service;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.UserExchange;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface OrderHistoryService {

    Optional<OrderHistory> save(Boolean buySatisfied, Boolean sellSatisfied, ExchangeProduct exchangeProduct,
                                UserExchange userExchange, ZonedDateTime originalTime,
                                CustomBigDecimal originalPrice, CustomBigDecimal total);

    void confirmBuySellExecutedWithSuccess(OrderHistory orderHistory);

    OrderHistory findTopByExchangeProductIdAndUserExchangeId(Long exchangeProductId, Long userExchangeId);

    void executeAll(ExchangeEnum exchangeEnum);

    void monitoringOpenOrders(ExchangeEnum exchangeEnum);
}
