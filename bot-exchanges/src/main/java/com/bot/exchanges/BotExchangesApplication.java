package com.bot.exchanges;

import com.bot.exchanges.commons.CommonExchangeProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    @ConfigurationProperties("exchanges.binance")
    public CommonExchangeProperties binanceProperties() {
        return new CommonExchangeProperties();
    }

    @Bean
    @ConfigurationProperties("exchanges.bittrex")
    public CommonExchangeProperties bittrexProperties() {
        return new CommonExchangeProperties();
    }

    @Bean
    @ConfigurationProperties("exchanges.cryptocompare")
    public CommonExchangeProperties cryptoCompareProperties() {
        return new CommonExchangeProperties();
    }

    @Bean
    @ConfigurationProperties("exchanges.alphavantage")
    public CommonExchangeProperties alphaVantageProperties() {
        return new CommonExchangeProperties();
    }
}
