package com.bot.exchanges.bittrex.client;

import static com.bot.exchanges.bittrex.utils.BittrexContants.BASE_URL;
import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET;
import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET_API_GET_OPEN_ORDERS;
import static com.bot.exchanges.bittrex.utils.BittrexContants.USER_ID;

import com.bot.exchanges.bittrex.BittrexFeignConfiguration;
import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.market.BittrexOpenOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "bittrex-market", url = BASE_URL, configuration = BittrexFeignConfiguration.class)
public interface BittrexMarketClient {

    @GetMapping(value = MARKET_API_GET_OPEN_ORDERS)
    AbstractBittrexDTO<List<BittrexOpenOrderDTO>> getOpenOrders(@RequestHeader(USER_ID) String userId,
                                                                @PathVariable(MARKET) String market);
}
