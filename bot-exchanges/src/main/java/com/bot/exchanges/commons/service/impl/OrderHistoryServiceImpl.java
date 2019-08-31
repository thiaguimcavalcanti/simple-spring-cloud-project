package com.bot.exchanges.commons.service.impl;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.entities.types.CustomBigDecimal;
import com.bot.exchanges.commons.enums.OrderStatusEnum;
import com.bot.exchanges.commons.enums.OrderTypeEnum;
import com.bot.exchanges.commons.repository.OrderHistoryRepository;
import com.bot.exchanges.commons.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    @Autowired
    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public void save(ExchangeProduct exchangeProduct, UserExchange userExchange, OrderTypeEnum orderTypeEnum) {
        ZonedDateTime tickDate = ZonedDateTime.now().withSecond(0).withNano(0);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setAmount(CustomBigDecimal.valueOf(1)); // get this value by user
        orderHistory.setOriginalDate(tickDate);
        orderHistory.setDate(tickDate);
        //orderHistory.setOriginalRate(marketSummaryDTO.getLast()); // get the current value
        //orderHistory.setRate(marketSummaryDTO.getLast()); // get the current value
        orderHistory.setExchangeProduct(exchangeProduct);
        orderHistory.setSimulation(Boolean.TRUE);
        orderHistory.setStatus(OrderStatusEnum.NEW);
        orderHistory.setUserExchange(userExchange);
        orderHistory.setType(orderTypeEnum);

        orderHistoryRepository.save(orderHistory);
    }

    @Override
    public OrderHistory findTopByExchangeProductIdAndUserExchangeId(Long exchangeProductId, Long userExchangeId) {
        return orderHistoryRepository.findTopByExchangeProductIdAndUserExchangeIdOrderByDateDesc(exchangeProductId, userExchangeId);
    }
}
