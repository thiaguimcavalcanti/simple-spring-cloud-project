package com.bot.exchanges.bittrex.client;

import com.bot.exchanges.bittrex.BittrexDefaultFeignConfiguration;
import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexCandlestickDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET_NAME;
import static com.bot.exchanges.bittrex.utils.BittrexContants.TICK_INTERVAL;

@FeignClient(name = "bittrex-2-0-public", url = "${exchanges.bittrex.baseUrl_2_0}",
        configuration = BittrexDefaultFeignConfiguration.class)
public interface BittrexPublic2Client {

    @GetMapping(value = "${exchanges.bittrex.apis.getTicks}")
    AbstractBittrexDTO<List<BittrexCandlestickDTO>> getCandlesticks(@PathVariable(TICK_INTERVAL) String ticketInterval,
                                                                    @PathVariable(MARKET_NAME) String marketName);
}
