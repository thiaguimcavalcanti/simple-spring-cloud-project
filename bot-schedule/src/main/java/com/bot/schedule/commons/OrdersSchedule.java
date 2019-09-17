package com.bot.schedule.commons;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class OrdersSchedule {

    protected ExchangeEnum exchangeEnum;

    private OrderService orderService;

    public OrdersSchedule(OrderService orderService) {
        this.orderService = orderService;
    }

    @Async
    @Scheduled(cron = "*/5 * * ? * *")
    public void monitoringOpenOrders() {
        orderService.monitoringOpenOrders(exchangeEnum);
    }

    @Async
    @Scheduled(cron = "*/20 * * ? * *")
    public void executeBuyOrSellOrdersTask() { orderService.executeBuyOrSellOrders(exchangeEnum); }

    @Async
    @Scheduled(cron = "0 * * ? * *")
    public void executeSellAnalysisTask() {

    }
}
