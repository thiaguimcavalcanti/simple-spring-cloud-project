package com.bot.exchanges.binance;

import org.springframework.context.annotation.Bean;

public class BinanceFeignConfiguration {

    @Bean
    public BinanceFeignConfiguration getAuthenticationInterceptor() {
        return new BinanceFeignConfiguration();
    }
}
