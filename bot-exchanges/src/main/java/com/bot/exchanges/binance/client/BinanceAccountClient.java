package com.bot.exchanges.binance.client;

import com.bot.exchanges.binance.BinanceAuthenticationFeignConfiguration;
import com.bot.exchanges.binance.dto.publicapi.BinanceOrderHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static com.bot.exchanges.binance.utils.BinanceConstants.END_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.FROM_ID;
import static com.bot.exchanges.binance.utils.BinanceConstants.LIMIT;
import static com.bot.exchanges.binance.utils.BinanceConstants.RECV_WINDOW;
import static com.bot.exchanges.binance.utils.BinanceConstants.START_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.SYMBOL;
import static com.bot.exchanges.binance.utils.BinanceConstants.TIMESTAMP;
import static com.bot.exchanges.commons.utils.CommonConstants.USER_ID;

@FeignClient(name = "binance-account", url = "${exchanges.binance.baseUrl}", configuration = BinanceAuthenticationFeignConfiguration.class)
public interface BinanceAccountClient {

    @GetMapping(value = "${exchanges.binance.apis.myTrades}")
    List<BinanceOrderHistoryDTO> getMyTrades(@RequestHeader(USER_ID) String userId,
                                             @PathVariable(SYMBOL) String symbol,
                                             @PathVariable(START_TIME) String startTime,
                                             @PathVariable(END_TIME) String endTime,
                                             @PathVariable(FROM_ID) String fromId,
                                             @PathVariable(LIMIT) Integer limit,
                                             @PathVariable(RECV_WINDOW) Integer recvWindow,
                                             @PathVariable(TIMESTAMP) Long timestamp);
}
