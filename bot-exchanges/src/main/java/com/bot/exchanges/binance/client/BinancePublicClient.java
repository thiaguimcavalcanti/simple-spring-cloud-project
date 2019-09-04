package com.bot.exchanges.binance.client;

import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceExchangeInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.bot.exchanges.binance.utils.BinanceConstants.END_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.INTERVAL;
import static com.bot.exchanges.binance.utils.BinanceConstants.LIMIT;
import static com.bot.exchanges.binance.utils.BinanceConstants.START_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.SYMBOL;

@FeignClient(name = "binance-public", url = "${exchanges.binance.baseUrl}")
public interface BinancePublicClient {

    @GetMapping(value = "${exchanges.binance.apis.klines}")
    List<BinanceCandlestickDTO> getCandlesticks(@PathVariable(SYMBOL) String symbol,
                                                @PathVariable(INTERVAL) String interval,
                                                @PathVariable(START_TIME) String startTime,
                                                @PathVariable(END_TIME) String endTime,
                                                @PathVariable(LIMIT) Integer limit);

    @GetMapping(value = "${exchanges.binance.apis.exchangeInfo}")
    BinanceExchangeInfoDTO getExchangeInfo();
}
