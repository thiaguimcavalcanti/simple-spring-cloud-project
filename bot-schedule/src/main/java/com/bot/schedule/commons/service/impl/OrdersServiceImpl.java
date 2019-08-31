package com.bot.schedule.commons.service.impl;

import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrderService {

    private final ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public OrdersServiceImpl(ExchangesApiFacade exchangesApiFacade) {
        this.exchangesApiFacade = exchangesApiFacade;
    }

    @Override
    public void monitoringOpenOrders(ExchangeEnum exchangeEnum) {
        System.out.println(exchangeEnum.name());
    }
}
