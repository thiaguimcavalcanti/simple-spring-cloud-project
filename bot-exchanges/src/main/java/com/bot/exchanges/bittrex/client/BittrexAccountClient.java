package com.bot.exchanges.bittrex.client;

import static com.bot.exchanges.bittrex.utils.BittrexContants.ACCOUNT_API_GET_BALANCES;
import static com.bot.exchanges.bittrex.utils.BittrexContants.ACCOUNT_API_GET_ORDER_HISTORY;
import static com.bot.exchanges.bittrex.utils.BittrexContants.BASE_URL;
import static com.bot.exchanges.bittrex.utils.BittrexContants.MARKET;
import static com.bot.exchanges.bittrex.utils.BittrexContants.USER_ID;

import com.bot.exchanges.bittrex.BittrexFeignConfiguration;
import com.bot.exchanges.bittrex.dto.AbstractBittrexDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexBalanceDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexOrderHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "bittrex-account", url = BASE_URL, configuration = BittrexFeignConfiguration.class)
public interface BittrexAccountClient {

    @GetMapping(value = ACCOUNT_API_GET_BALANCES)
    AbstractBittrexDTO<List<BittrexBalanceDTO>> getBalances(@RequestHeader(USER_ID) String userId);

    @GetMapping(value = ACCOUNT_API_GET_ORDER_HISTORY)
    AbstractBittrexDTO<List<BittrexOrderHistoryDTO>> getOrderHistory(@RequestHeader(USER_ID) String userId,
                                                                     @PathVariable(MARKET) String market);
}
