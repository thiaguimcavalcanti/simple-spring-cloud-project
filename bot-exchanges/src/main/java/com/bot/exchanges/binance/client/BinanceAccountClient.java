package com.bot.exchanges.binance.client;

import com.bot.commons.dto.OrderDTO;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.exchanges.binance.BinanceAuthenticationFeignConfiguration;
import com.bot.exchanges.binance.dto.account.BinanceOrderHistoryDTO;
import com.bot.exchanges.binance.enums.BinanceOrderTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.ta4j.core.num.Num;

import java.util.List;

import static com.bot.exchanges.binance.utils.BinanceConstants.END_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.FROM_ID;
import static com.bot.exchanges.binance.utils.BinanceConstants.LIMIT;
import static com.bot.exchanges.binance.utils.BinanceConstants.ORDER_ID;
import static com.bot.exchanges.binance.utils.BinanceConstants.PRICE;
import static com.bot.exchanges.binance.utils.BinanceConstants.QUANTITY;
import static com.bot.exchanges.binance.utils.BinanceConstants.RECV_WINDOW;
import static com.bot.exchanges.binance.utils.BinanceConstants.SIDE;
import static com.bot.exchanges.binance.utils.BinanceConstants.START_TIME;
import static com.bot.exchanges.binance.utils.BinanceConstants.SYMBOL;
import static com.bot.exchanges.binance.utils.BinanceConstants.TIMESTAMP;
import static com.bot.exchanges.binance.utils.BinanceConstants.TYPE;
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

    @PostMapping(value = "${exchanges.binance.apis.newOrder}")
    OrderDTO createNewOrder(@RequestHeader(USER_ID) String userId,
                            @PathVariable(SYMBOL) String symbol,
                            @PathVariable(SIDE) OrderTypeEnum side,
                            @PathVariable(TYPE) BinanceOrderTypeEnum type,
                            @PathVariable(QUANTITY) Num quantity,
                            @PathVariable(PRICE) Num price,
                            @PathVariable(RECV_WINDOW) Integer recvWindow,
                            @PathVariable(TIMESTAMP) Long timestamp);

    @PostMapping(value = "${exchanges.binance.apis.newOrderTest}")
    OrderDTO createNewOrderTest(@RequestHeader(USER_ID) String userId,
                                @PathVariable(SYMBOL) String symbol,
                                @PathVariable(SIDE) OrderTypeEnum side,
                                @PathVariable(TYPE) BinanceOrderTypeEnum type,
                                @PathVariable(QUANTITY) Num quantity,
                                @PathVariable(PRICE) Num price,
                                @PathVariable(RECV_WINDOW) Integer recvWindow,
                                @PathVariable(TIMESTAMP) Long timestamp);

    @DeleteMapping(value = "${exchanges.binance.apis.cancelOrder}")
    OrderDTO cancelOrder(@RequestHeader(USER_ID) String userId,
                         @PathVariable(SYMBOL) String symbol,
                         @PathVariable(ORDER_ID) String orderId,
                         @PathVariable(RECV_WINDOW) Integer recvWindow,
                         @PathVariable(TIMESTAMP) Long timestamp);

    @GetMapping(value = "${exchanges.binance.apis.openOrders}")
    List<OrderDTO> getOpenOrders(@RequestHeader(USER_ID) String userId,
                                 @PathVariable(SYMBOL) String symbol,
                                 @PathVariable(RECV_WINDOW) Integer recvWindow,
                                 @PathVariable(TIMESTAMP) Long timestamp);

    @GetMapping(value = "${exchanges.binance.apis.allOrders}")
    List<OrderDTO> getAllOrders(@RequestHeader(USER_ID) String userId,
                                @PathVariable(SYMBOL) String symbol,
                                @PathVariable(RECV_WINDOW) Integer recvWindow,
                                @PathVariable(TIMESTAMP) Long timestamp);
}
