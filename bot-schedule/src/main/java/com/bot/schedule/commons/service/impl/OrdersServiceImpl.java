package com.bot.schedule.commons.service.impl;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.client.OrderClient;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrderService {

    private final OrderClient orderClient;

    @Autowired
    public OrdersServiceImpl(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Override
    public void monitoringOpenOrders(ExchangeEnum exchangeEnum) { orderClient.monitoringOpenOrders(exchangeEnum); }

    @Override
    public void executeBuyOrSellOrders(ExchangeEnum exchangeEnum) {
        orderClient.executeAll(exchangeEnum);
    }
}
