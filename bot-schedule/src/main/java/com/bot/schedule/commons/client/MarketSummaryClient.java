package com.bot.schedule.commons.client;

import com.bot.commons.enums.ExchangeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.bot.schedule.commons.utils.ExchangesConstants.EXCHANGE_ENUM;

@FeignClient(name = "market-summary-client", url = "${exchanges.baseUrl}${exchanges.apis.market-summary.basePath}")
public interface MarketSummaryClient {

    @GetMapping(value = "${exchanges.apis.market-summary.refreshAll}")
    void refreshAll(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum);
}
