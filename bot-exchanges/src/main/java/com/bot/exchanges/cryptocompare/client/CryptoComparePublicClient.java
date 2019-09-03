package com.bot.exchanges.cryptocompare.client;

import com.bot.exchanges.cryptocompare.CryptoCompareFeignConfiguration;
import com.bot.exchanges.cryptocompare.dto.AbstractCryptoCompareDTO;
import com.bot.exchanges.cryptocompare.dto.CryptoCompareCandlestickDTO;
import com.bot.exchanges.cryptocompare.dto.CryptoCompareCoinListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.AGGREGATE;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.EXCHANGE;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.FSYM;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.LIMIT;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.TRY_CONVERSION;
import static com.bot.exchanges.cryptocompare.utils.CryptoCompareContants.TSYM;

@FeignClient(name = "crypto-compare-public", url = "${exchanges.cryptoCompare.baseUrl}", configuration = CryptoCompareFeignConfiguration.class)
public interface CryptoComparePublicClient {

    @GetMapping(value = "${exchanges.cryptoCompare.apis.coinList}")
    CryptoCompareCoinListDTO getCoinList();

    @GetMapping(value = "${exchanges.cryptoCompare.apis.histoMinute}")
    AbstractCryptoCompareDTO<CryptoCompareCandlestickDTO> historicalMinute(@PathVariable(FSYM) String fsym,
                                                                           @PathVariable(TSYM) String tsym,
                                                                           @PathVariable(LIMIT) int limit,
                                                                           @PathVariable(EXCHANGE) String exchange,
                                                                           @PathVariable(AGGREGATE) long aggregate,
                                                                           @PathVariable(TRY_CONVERSION) boolean tryConversion);

    @GetMapping(value = "${exchanges.cryptoCompare.apis.histoHour}")
    AbstractCryptoCompareDTO<CryptoCompareCandlestickDTO> historicalHour(@PathVariable(FSYM) String fsym,
                                                                         @PathVariable(TSYM) String tsym,
                                                                         @PathVariable(LIMIT) int limit,
                                                                         @PathVariable(EXCHANGE) String exchange,
                                                                         @PathVariable(AGGREGATE) long aggregate,
                                                                         @PathVariable(TRY_CONVERSION) boolean tryConversion);
}
