package com.bot.exchanges.cryptocompare.client;

import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.AGGREGATE;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.BASE_URL;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.EXCHANGE;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.FSYM;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.LIMIT;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.PUBLIC_API_COIN_LIST;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.PUBLIC_API_HISTORICAL_HOUR;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.PUBLIC_API_HISTORICAL_MINUTE;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.TRY_CONVERSION;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.TSYM;

import com.bot.exchanges.cryptocompare.CryptoCompareFeignConfiguration;
import com.bot.exchanges.cryptocompare.dto.AbstractCryptoCompareDTO;
import com.bot.exchanges.cryptocompare.dto.CryptoCompareCandlestickDTO;
import com.bot.exchanges.cryptocompare.dto.CryptoCompareCoinListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "crypto-compare-public", url = BASE_URL, configuration = CryptoCompareFeignConfiguration.class)
public interface CryptoComparePublicClient {

    @GetMapping(value = PUBLIC_API_COIN_LIST)
    CryptoCompareCoinListDTO getCoinList();

    @GetMapping(value = PUBLIC_API_HISTORICAL_MINUTE)
    AbstractCryptoCompareDTO<CryptoCompareCandlestickDTO> historicalMinute(@PathVariable(FSYM) String fsym,
                                                                           @PathVariable(TSYM) String tsym,
                                                                           @PathVariable(LIMIT) int limit,
                                                                           @PathVariable(EXCHANGE) String exchange,
                                                                           @PathVariable(AGGREGATE) long aggregate,
                                                                           @PathVariable(TRY_CONVERSION) boolean tryConversion);

    @GetMapping(value = PUBLIC_API_HISTORICAL_HOUR)
    AbstractCryptoCompareDTO<CryptoCompareCandlestickDTO> historicalHour(@PathVariable(FSYM) String fsym,
                                                                         @PathVariable(TSYM) String tsym,
                                                                         @PathVariable(LIMIT) int limit,
                                                                         @PathVariable(EXCHANGE) String exchange,
                                                                         @PathVariable(AGGREGATE) long aggregate,
                                                                         @PathVariable(TRY_CONVERSION) boolean tryConversion);
}
