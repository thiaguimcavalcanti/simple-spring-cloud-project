package com.bot.exchanges.cryptocompare.client;

import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.BASE_URL;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.PUBLIC_API_COIN_LIST;

import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.cryptocompare.dto.AbstractCryptoCompareDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "crypto-compare-public", url = BASE_URL)
public interface CryptoComparePublicClient {

    @GetMapping(value = PUBLIC_API_COIN_LIST)
    AbstractCryptoCompareDTO<Product> getCoinList();
}
