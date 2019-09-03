package com.bot.exchanges.bittrex.client;

import com.bot.exchanges.bittrex.BittrexFeignConfiguration;
import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.market.BittrexOpenOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET;
import static com.bot.exchanges.bittrex.utils.BittrexContants.USER_ID;

@FeignClient(name = "bittrex-market", url = "${exchanges.bittrex.baseUrl}", configuration = BittrexFeignConfiguration.class)
public interface BittrexMarketClient {

    @GetMapping(value = "${exchanges.bittrex.apis.getOpenOrders}")
    AbstractBittrexDTO<List<BittrexOpenOrderDTO>> getOpenOrders(@RequestHeader(USER_ID) String userId,
                                                                @PathVariable(MARKET) String market);
}
