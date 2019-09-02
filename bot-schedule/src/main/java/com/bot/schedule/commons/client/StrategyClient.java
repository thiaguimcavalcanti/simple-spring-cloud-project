package com.bot.schedule.commons.client;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "strategies-client", url = "${exchanges.baseUrl}${exchanges.apis.strategy.basePath}")
public interface StrategyClient {

    @GetMapping(value = "${exchanges.apis.strategy.analyze}")
    void analyzeStrategies(@PathVariable("exchangeEnum")  ExchangeEnum exchangeEnum,
                           @PathVariable("baseProductId") String baseProductId,
                           @PathVariable("productId") String productId,
                           @PathVariable("periodEnum") PeriodEnum periodEnum);

    @GetMapping(value = "${exchanges.apis.strategy.monitoring}")
    void monitoringStrategies(@PathVariable("exchangeEnum")  ExchangeEnum exchangeEnum);
}
