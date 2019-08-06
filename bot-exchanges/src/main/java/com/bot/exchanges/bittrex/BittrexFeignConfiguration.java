package com.bot.exchanges.bittrex;

import com.bot.exchanges.bittrex.interceptor.BittrexAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;

public class BittrexFeignConfiguration {

    @Bean
    public BittrexAuthenticationInterceptor getAuthenticationInterceptor() {
        return new BittrexAuthenticationInterceptor();
    }
}
