package com.bot.ticks.service.impl;

import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.ticks.client.OrdersClient;
import com.bot.ticks.service.TicksService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicksServiceImpl implements TicksService {

    private final OrdersClient ordersClient;

    private final ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public TicksServiceImpl(OrdersClient ordersClient,
                            ExchangesApiFacade exchangesApiFacade) {
        this.ordersClient = ordersClient;
        this.exchangesApiFacade = exchangesApiFacade;
    }

    @Override
    public TickerDTO getTicker(String market) {
        return exchangesApiFacade.getTicker(ExchangeEnum.BITTREX, market);
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    @Override
    public String ticks() {
        return ordersClient.greeting();
    }

    @Override
    public List<BalanceDTO> getBalances() {
        return (List<BalanceDTO>) exchangesApiFacade.getBalances(ExchangeEnum.BITTREX, "USER_ID");
    }

    public String fallbackMethod(Throwable exception) {
        return "error: " + exception.getMessage();
    }
}
