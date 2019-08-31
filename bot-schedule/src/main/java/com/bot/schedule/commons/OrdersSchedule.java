package com.bot.schedule.commons;

import com.bot.exchanges.commons.enums.ExchangeEnum;
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
    @Scheduled(cron = "*/1 * * * * ?")
    public void monitoringOpenOrders() {
        orderService.monitoringOpenOrders(exchangeEnum);
    }

    @Async
    @Scheduled(cron = "*/20 * * * * ?")
    public void executeBuyOrSellOrdersTask() {

    }

    @Async
    @Scheduled(cron = "* */1 * * * ?")
    public void executeSellAnalysisTask() {

    }
}
