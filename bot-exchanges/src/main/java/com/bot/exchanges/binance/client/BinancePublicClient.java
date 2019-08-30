package com.bot.exchanges.binance.client;

import static com.bot.exchanges.binance.utils.BinanceConstants.BASE_URL;
import static com.bot.exchanges.binance.utils.BinanceConstants.END_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.INTERVAL;
import static com.bot.exchanges.binance.utils.BinanceConstants.LIMIT;
import static com.bot.exchanges.binance.utils.BinanceConstants.PUBLIC_API_EXCHANGE_INFO;
import static com.bot.exchanges.binance.utils.BinanceConstants.PUBLIC_API_KLINES;
import static com.bot.exchanges.binance.utils.BinanceConstants.START_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.SYMBOL;

import com.bot.exchanges.binance.BinanceFeignConfiguration;
import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceExchangeInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "binance-public", url = BASE_URL, configuration = BinanceFeignConfiguration.class)
public interface BinancePublicClient {

    @GetMapping(value = PUBLIC_API_KLINES)
    List<BinanceCandlestickDTO> getCandlesticks(@PathVariable(SYMBOL) String symbol,
                                                @PathVariable(INTERVAL) String interval,
                                                @PathVariable(START_TIME) String startTime,
                                                @PathVariable(END_TIME) String endTime,
                                                @PathVariable(LIMIT) Integer limit);

    @GetMapping(value = PUBLIC_API_EXCHANGE_INFO)
    BinanceExchangeInfoDTO getExchangeInfo();
}
