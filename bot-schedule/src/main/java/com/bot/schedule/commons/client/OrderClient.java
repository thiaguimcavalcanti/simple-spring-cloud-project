package com.bot.schedule.commons.client;

import com.bot.commons.enums.ExchangeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.bot.schedule.commons.utils.ExchangesConstants.EXCHANGE_ENUM;

@FeignClient(name = "order-client", url = "${exchanges.baseUrl}${exchanges.apis.order.basePath}")
public interface OrderClient {

    @GetMapping(value = "${exchanges.apis.order.executeAll}")
    void executeAll(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum);

    @GetMapping(value = "${exchanges.apis.order.monitoringOpenOrders}")
    void monitoringOpenOrders(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum);
}
