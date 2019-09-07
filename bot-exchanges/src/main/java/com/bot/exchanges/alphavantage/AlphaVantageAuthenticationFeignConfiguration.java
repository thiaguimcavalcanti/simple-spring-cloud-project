package com.bot.exchanges.alphavantage;

import com.bot.exchanges.alphavantage.interceptor.AlphaVantageAuthenticationInterceptor;
import com.bot.exchanges.binance.interceptor.BinanceAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;

public class AlphaVantageAuthenticationFeignConfiguration {

    @Bean
    public AlphaVantageAuthenticationInterceptor getAuthenticationInterceptor() {
        return new AlphaVantageAuthenticationInterceptor();
    }
}
