package com.bot.schedule.commons.client;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "candlesticks-client", url = "${exchanges.baseUrl}${exchanges.apis.candlestick.basePath}")
public interface CandlestickClient {

    @GetMapping(value = "${exchanges.apis.candlestick.refreshLatestCandlestick}")
    CandlestickDTO refreshLatestCandlestick(@PathVariable("exchangeEnum") ExchangeEnum exchangeEnum,
                                            @PathVariable("baseProductId") String baseProductId,
                                            @PathVariable("productId") String productId,
                                            @PathVariable("periodEnum") PeriodEnum periodEnum);
}
