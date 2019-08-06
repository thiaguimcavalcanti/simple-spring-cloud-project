package com.bot.ticks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.bot"})
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class BotTicksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotTicksApplication.class, args);
    }

}
