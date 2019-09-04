package com.bot.exchanges.binance;

import com.bot.exchanges.binance.interceptor.BinanceAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;

public class BinanceAuthenticationFeignConfiguration {

    @Bean
    public BinanceAuthenticationInterceptor getAuthenticationInterceptor() {
        return new BinanceAuthenticationInterceptor();
    }
}
