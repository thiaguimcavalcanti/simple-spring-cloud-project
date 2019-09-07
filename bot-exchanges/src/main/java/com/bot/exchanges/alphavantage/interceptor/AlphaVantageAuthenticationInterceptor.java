package com.bot.exchanges.alphavantage.interceptor;

import com.bot.exchanges.alphavantage.utils.AlphaVantageContants;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import static com.bot.exchanges.alphavantage.utils.AlphaVantageContants.API_KEY;

public class AlphaVantageAuthenticationInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query(API_KEY, "");
    }
}