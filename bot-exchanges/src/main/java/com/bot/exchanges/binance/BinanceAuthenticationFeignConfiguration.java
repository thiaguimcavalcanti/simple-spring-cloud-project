package com.bot.exchanges.binance;

import com.bot.commons.exceptions.AppException;
import com.bot.exchanges.binance.interceptor.BinanceAuthenticationInterceptor;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class BinanceAuthenticationFeignConfiguration {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Bean
    public BinanceAuthenticationInterceptor getAuthenticationInterceptor() {
        return new BinanceAuthenticationInterceptor();
    }
}
