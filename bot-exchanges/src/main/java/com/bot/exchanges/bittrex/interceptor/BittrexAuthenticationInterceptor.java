package com.bot.exchanges.bittrex.interceptor;

import com.bot.exchanges.bittrex.BittrexProperties;
import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.repository.UserExchangeRepository;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.bot.exchanges.bittrex.utils.BittrexContants.API_KEY;
import static com.bot.exchanges.bittrex.utils.BittrexContants.API_SIGN;
import static com.bot.exchanges.bittrex.utils.BittrexContants.NONCE;
import static com.bot.exchanges.bittrex.utils.BittrexContants.USER_ID;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.HMAC_SHA_512;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.calculateHash;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.generateNonce;

public class BittrexAuthenticationInterceptor implements RequestInterceptor {

    @Autowired
    private UserExchangeRepository userExchangeRepository;

    @Autowired
    private BittrexProperties bittrexProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        List<String> id = (List<String>) requestTemplate.request().headers().get(USER_ID);

        if (id != null && !id.isEmpty()) {
            UserExchange userExchange = userExchangeRepository.findByUserId(id.get(0));

            if (userExchange != null) {
                requestTemplate.query(API_KEY, userExchange.getKey());
                requestTemplate.query(NONCE, generateNonce());

                String url = bittrexProperties.getBaseUrl() + requestTemplate.request().url();
                String apiSign = calculateHash(userExchange.getSecret(), url, HMAC_SHA_512);

                requestTemplate.header(API_SIGN, apiSign);
            }
        }
    }
}