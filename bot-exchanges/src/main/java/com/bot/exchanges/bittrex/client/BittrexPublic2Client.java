package com.bot.exchanges.bittrex.client;

import static com.bot.exchanges.bittrex.utils.BittrexContants.BASE_URL_2_0;
import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET_NAME;
import static com.bot.exchanges.bittrex.utils.BittrexContants.PUBLIC_API_GET_TICKS;
import static com.bot.exchanges.bittrex.utils.BittrexContants.TICK_INTERVAL;

import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexCandlestickDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "bittrex-2-0-public", url = BASE_URL_2_0)
public interface BittrexPublic2Client {

    @GetMapping(value = PUBLIC_API_GET_TICKS)
    AbstractBittrexDTO<List<BittrexCandlestickDTO>> getTicks( @PathVariable(MARKET_NAME) String marketName,
                                                              @PathVariable(TICK_INTERVAL) String ticketInterval);
}
