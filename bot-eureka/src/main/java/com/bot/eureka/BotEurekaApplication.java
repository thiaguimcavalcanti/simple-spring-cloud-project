package com.bot.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BotEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotEurekaApplication.class, args);
	}

}
