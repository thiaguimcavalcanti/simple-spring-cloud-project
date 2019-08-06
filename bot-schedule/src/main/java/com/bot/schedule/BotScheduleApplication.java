package com.bot.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"com.bot"})
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class BotScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotScheduleApplication.class, args);
    }

}
