package com.bot.exchanges.bittrex.interceptor;

import static com.bot.exchanges.bittrex.utils.BittrexContants.API_KEY;
import static com.bot.exchanges.bittrex.utils.BittrexContants.API_SIGN;
import static com.bot.exchanges.bittrex.utils.BittrexContants.BASE_URL;
import static com.bot.exchanges.bittrex.utils.BittrexContants.NONCE;
import static com.bot.exchanges.bittrex.utils.BittrexContants.USER_ID;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.HMAC_SHA_512;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.calculateHash;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.generateNonce;

import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.repository.UserExchangeRepository;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BittrexAuthenticationInterceptor implements RequestInterceptor {

    @Autowired
    private UserExchangeRepository userExchangeRepository;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        List<String> id = (List<String>) requestTemplate.request().headers().remove(USER_ID);

        if (id != null && !id.isEmpty()) {
            UserExchange userExchange = userExchangeRepository.findByUserId(id.get(0));

            requestTemplate.query(API_KEY, userExchange.getKey());
            requestTemplate.query(NONCE, generateNonce());

            String url = BASE_URL + requestTemplate.request().url();
            String apiSign = calculateHash(userExchange.getSecret(), url, HMAC_SHA_512);

            requestTemplate.header(API_SIGN, apiSign);
        }
    }
}