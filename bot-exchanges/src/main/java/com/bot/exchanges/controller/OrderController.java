package com.bot.exchanges.controller;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderHistoryService orderHistoryService;

    @Autowired
    public OrderController(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping("/executeAll")
    public void executeAll(@RequestParam ExchangeEnum exchangeEnum) {
        orderHistoryService.executeAll(exchangeEnum);
    }

    @GetMapping("/monitoringOpenOrders")
    public void monitoringOpenOrders(@RequestParam ExchangeEnum exchangeEnum) {
        orderHistoryService.monitoringOpenOrders(exchangeEnum);
    }
}
