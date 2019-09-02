package com.bot.schedule.commons.service.impl;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.client.StrategyClient;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrderService {

    private final StrategyClient exchangesClient;

    @Autowired
    public OrdersServiceImpl(StrategyClient exchangesClient) {
        this.exchangesClient = exchangesClient;
    }

    @Override
    public void monitoringOpenOrders(ExchangeEnum exchangeEnum) {

    }
}
