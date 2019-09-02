package com.bot.exchanges.commons.service;

import com.bot.commons.enums.OrderTypeEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.UserExchange;

public interface OrderHistoryService {

    void save(ExchangeProduct exchangeProduct, UserExchange userExchange, OrderTypeEnum orderTypeEnum);

    OrderHistory findTopByExchangeProductIdAndUserExchangeId(Long exchangeProductId, Long userExchangeId);
}
