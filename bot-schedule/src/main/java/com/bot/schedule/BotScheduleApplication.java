package com.bot.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class BotScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotScheduleApplication.class, args);
    }

}
