package com.bot.schedule.commons.service;

import com.bot.exchanges.commons.enums.ExchangeEnum;

public interface OrderService {

    void monitoringOpenOrders(ExchangeEnum exchangeEnum);
}
