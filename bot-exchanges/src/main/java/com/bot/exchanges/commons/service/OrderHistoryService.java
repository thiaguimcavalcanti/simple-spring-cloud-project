package com.bot.exchanges.commons.service;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.enums.OrderTypeEnum;

public interface OrderHistoryService {

    void save(ExchangeProduct exchangeProduct, UserExchange userExchange, OrderTypeEnum orderTypeEnum);

    OrderHistory findTopByExchangeProductIdAndUserExchangeId(Long exchangeProductId, Long userExchangeId);
}
