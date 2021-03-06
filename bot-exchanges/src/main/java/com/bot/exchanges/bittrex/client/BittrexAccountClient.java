package com.bot.exchanges.bittrex.client;

import com.bot.exchanges.bittrex.BittrexAuthenticationFeignConfiguration;
import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexBalanceDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexOrderHistoryDTO;
import com.bot.exchanges.commons.utils.CommonConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET;
import static com.bot.exchanges.commons.utils.CommonConstants.USER_ID;

@FeignClient(name = "bittrex-account", url = "${exchanges.bittrex.baseUrl}", configuration = BittrexAuthenticationFeignConfiguration.class)
public interface BittrexAccountClient {

    @GetMapping(value = "${exchanges.bittrex.apis.getBalances}")
    AbstractBittrexDTO<List<BittrexBalanceDTO>> getBalances(@RequestHeader(USER_ID) String userId);

    @GetMapping(value = "${exchanges.bittrex.apis.getOpenHistory}")
    AbstractBittrexDTO<List<BittrexOrderHistoryDTO>> getOrderHistory(@RequestHeader(USER_ID) String userId,
                                                                     @PathVariable(MARKET) String market);
}
