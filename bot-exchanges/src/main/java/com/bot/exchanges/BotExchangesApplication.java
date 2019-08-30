package com.bot.exchanges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class BotExchangesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotExchangesApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // set JVM timezone as UTC
    }
}
