package com.bot.exchanges.bittrex;

import com.bot.exchanges.commons.utils.CustomRetryer;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class BittrexDefaultFeignConfiguration {

    @Bean
    public Retryer retryer() {
        return new CustomRetryer();
    }
}
