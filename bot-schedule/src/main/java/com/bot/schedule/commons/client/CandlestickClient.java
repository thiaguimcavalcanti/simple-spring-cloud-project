package com.bot.schedule.commons.client;

import com.bot.commons.dto.BaseListDTO;
import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.bot.schedule.commons.utils.ExchangesConstants.BASE_PRODUCT_ID;
import static com.bot.schedule.commons.utils.ExchangesConstants.EXCHANGE_ENUM;
import static com.bot.schedule.commons.utils.ExchangesConstants.PERIOD_ENUM;
import static com.bot.schedule.commons.utils.ExchangesConstants.PRODUCT_ID;

@FeignClient(name = "candlesticks-client", url = "${exchanges.baseUrl}${exchanges.apis.candlestick.basePath}")
public interface CandlestickClient {

    @GetMapping(value = "${exchanges.apis.candlestick.refreshLatestCandlestick}")
    void refreshLatestCandlestick(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum,
                                  @PathVariable(BASE_PRODUCT_ID) String baseProductId,
                                  @PathVariable(PRODUCT_ID) String productId,
                                  @PathVariable(PERIOD_ENUM) PeriodEnum periodEnum);

    @PostMapping(value = "${exchanges.apis.candlestick.refreshLatestCandlesticks}")
    void refreshLatestCandlesticks(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum,
                                   @RequestBody BaseListDTO<? extends CandlestickDTO> candlesticks);
}
