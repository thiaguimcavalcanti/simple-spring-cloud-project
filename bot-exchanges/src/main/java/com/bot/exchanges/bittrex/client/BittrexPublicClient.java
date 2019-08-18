package com.bot.exchanges.bittrex.client;

import static com.bot.exchanges.bittrex.utils.BittrexContants.BASE_URL;
import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET;
import static com.bot.exchanges.bittrex.utils.BittrexContants.PUBLIC_API_GET_MARKETS;
import static com.bot.exchanges.bittrex.utils.BittrexContants.PUBLIC_API_TICKER;

import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexExchangeProductDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexTickerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "bittrex-public", url = BASE_URL)
public interface BittrexPublicClient {

    @GetMapping(value = PUBLIC_API_TICKER)
    AbstractBittrexDTO<BittrexTickerDTO> ticker(@PathVariable(MARKET) String market);

    @GetMapping(value = PUBLIC_API_GET_MARKETS)
    AbstractBittrexDTO<List<BittrexExchangeProductDTO>> getMarkets();
}
