package com.bot.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BotScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotScheduleApplication.class, args);
    }

}
