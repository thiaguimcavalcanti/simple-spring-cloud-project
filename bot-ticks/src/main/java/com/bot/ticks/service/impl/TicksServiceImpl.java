package com.bot.ticks.service.impl;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.ticks.client.OrdersClient;
import com.bot.ticks.service.TicksService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicksServiceImpl implements TicksService {

    private final OrdersClient ordersClient;

    @Autowired
    public TicksServiceImpl(OrdersClient ordersClient) {
        this.ordersClient = ordersClient;
    }

    @Override
    public TickerDTO getTicker(String market) {
        return null;
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    @Override
    public String ticks() {
        return ordersClient.greeting();
    }

    @Override
    public List<BalanceDTO> getBalances() {
        return null;
    }

    public String fallbackMethod(Throwable exception) {
        return "error: " + exception.getMessage();
    }
}
