package com.bot.ticks.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("bot-orders")
public interface OrdersClient {

    @GetMapping("/greeting")
    String greeting();

}
