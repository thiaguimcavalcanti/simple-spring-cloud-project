package com.bot.schedule.commons.service;


import com.bot.commons.enums.ExchangeEnum;

public interface OrderService {

    void monitoringOpenOrders(ExchangeEnum exchangeEnum);

    void executeBuyOrSellOrders(ExchangeEnum exchangeEnum);
}
