package com.bot.exchanges.bittrex;

import com.bot.exchanges.bittrex.interceptor.BittrexAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;

public class BittrexAuthenticationFeignConfiguration {

    @Bean
    public BittrexAuthenticationInterceptor getAuthenticationInterceptor() {
        return new BittrexAuthenticationInterceptor();
    }
}
