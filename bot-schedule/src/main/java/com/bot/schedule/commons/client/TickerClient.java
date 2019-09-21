package com.bot.schedule.commons.client;

import com.bot.commons.dto.BaseListDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.bot.schedule.commons.utils.ExchangesConstants.EXCHANGE_ENUM;

@FeignClient(name = "ticker-client", url = "${exchanges.baseUrl}${exchanges.apis.ticker.basePath}")
public interface TickerClient {

    @PostMapping(value = "${exchanges.apis.ticker.refreshAll}")
    void refreshAll(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum,
                    @RequestBody BaseListDTO<? extends TickerDTO> tickers);
}
