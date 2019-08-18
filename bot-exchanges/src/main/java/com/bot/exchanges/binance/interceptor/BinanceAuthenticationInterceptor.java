package com.bot.exchanges.binance.interceptor;

import static com.bot.exchanges.binance.utils.BinanceConstants.API_KEY;
import static com.bot.exchanges.binance.utils.BinanceConstants.BASE_URL;
import static com.bot.exchanges.binance.utils.BinanceConstants.SIGNATURE;
import static com.bot.exchanges.binance.utils.BinanceConstants.USER_ID;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.HMAC_SHA_256;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.calculateHash;

import com.bot.exchanges.commons.entities.Authentication;
import com.bot.exchanges.commons.repository.AuthenticationRepository;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BinanceAuthenticationInterceptor implements RequestInterceptor {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        List<String> id = (List<String>) requestTemplate.request().headers().remove(USER_ID);

        if (id != null && !id.isEmpty()) {
            Authentication authentication = authenticationRepository.findByUserId(id.get(0));

            String url = BASE_URL + requestTemplate.request().url();
            String signature = calculateHash(authentication.getSecret(), url, HMAC_SHA_256);
            requestTemplate.query(SIGNATURE, signature);

            requestTemplate.header(API_KEY, authentication.getKey());
        }
    }
}