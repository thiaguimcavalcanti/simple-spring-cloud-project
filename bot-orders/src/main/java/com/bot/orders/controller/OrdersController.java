package com.bot.orders.controller;

import com.netflix.discovery.EurekaClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {

    private final EurekaClient eurekaClient;

    private static final Logger LOG = LogManager.getLogger(OrdersController.class.getName());

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public OrdersController(@Lazy EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @GetMapping("/greeting")
    public String greeting() {
        LOG.info("Orders method");
        return String.format("Hello from '%s'!", eurekaClient.getApplication(appName).getName());
    }
}
