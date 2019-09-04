package com.bot.schedule.commons.client;

import com.bot.commons.enums.ExchangeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.bot.schedule.commons.utils.ExchangesConstants.EXCHANGE_ENUM;

@FeignClient(name = "strategies-client", url = "${exchanges.baseUrl}${exchanges.apis.strategy.basePath}")
public interface StrategyClient {

    @GetMapping(value = "${exchanges.apis.strategy.monitoring}")
    void monitoringStrategies(@PathVariable(EXCHANGE_ENUM) ExchangeEnum exchangeEnum);
}
