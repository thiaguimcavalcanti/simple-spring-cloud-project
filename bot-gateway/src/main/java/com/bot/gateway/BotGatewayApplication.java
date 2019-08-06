package com.bot.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class BotGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotGatewayApplication.class, args);
    }
}
