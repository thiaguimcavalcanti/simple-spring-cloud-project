package com.bot.exchanges.binance.interceptor;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.binance.BinanceProperties;
import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.repository.UserExchangeRepository;
import com.bot.exchanges.commons.utils.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.bot.exchanges.binance.utils.BinanceConstants.API_KEY;
import static com.bot.exchanges.binance.utils.BinanceConstants.SIGNATURE;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.HMAC_SHA_256;
import static com.bot.exchanges.bittrex.utils.EncryptionUtils.calculateHash;
import static com.bot.exchanges.commons.utils.CommonConstants.USER_ID;

public class BinanceAuthenticationInterceptor implements RequestInterceptor {

    @Autowired
    private UserExchangeRepository userExchangeRepository;

    @Autowired
    private BinanceProperties binanceProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        List<String> id = (List<String>) requestTemplate.request().headers().get(USER_ID);

        if (id != null && !id.isEmpty()) {
            UserExchange userExchange = userExchangeRepository.findByUserIdAndExchangeId(id.get(0),
                    ExchangeEnum.BINANCE.getId());

            if (userExchange != null) {
                String params = requestTemplate.request().url().split("\\?")[1];
                String signature = calculateHash(userExchange.getSecret(), params, HMAC_SHA_256);
                requestTemplate.query(SIGNATURE, signature);

                requestTemplate.header(API_KEY, userExchange.getKey());
            }
        }
    }
}