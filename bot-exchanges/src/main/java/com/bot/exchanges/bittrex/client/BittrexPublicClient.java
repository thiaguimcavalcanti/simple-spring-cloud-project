package com.bot.exchanges.bittrex.client;

import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexExchangeProductDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexTickerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET;

@FeignClient(name = "bittrex-public", url = "${exchanges.bittrex.baseUrl}")
public interface BittrexPublicClient {

    @GetMapping(value = "${exchanges.bittrex.apis.getTicker}")
    AbstractBittrexDTO<BittrexTickerDTO> ticker(@PathVariable(MARKET) String market);

    @GetMapping(value = "${exchanges.bittrex.apis.getMarkets}")
    AbstractBittrexDTO<List<BittrexExchangeProductDTO>> getMarkets();
}
