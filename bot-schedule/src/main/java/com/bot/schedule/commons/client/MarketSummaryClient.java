package com.bot.schedule.commons.client;

import com.bot.commons.dto.BaseListDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.enums.ExchangeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.bot.schedule.commons.utils.ExchangesConstants.EXCHANGE_ENUM;

@FeignClient(name = "market-summary-client", url = "${exchanges.baseUrl}${exchanges.apis.market-summary.basePath}")
public interface MarketSummaryClient {

    @PostMapping(value = "${exchanges.apis.market-summary.refreshAll}")
    void refreshAll(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum,
                    @RequestBody BaseListDTO<MarketSummaryDTO> marketSummaries);

    @GetMapping(value = "${exchanges.apis.market-summary.refreshAll}")
    void refreshAll(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum);
}
