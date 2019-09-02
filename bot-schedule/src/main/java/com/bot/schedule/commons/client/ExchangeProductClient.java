package com.bot.schedule.commons.client;

import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.enums.ExchangeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "exchangeProduct-client", url = "${exchanges.baseUrl}${exchanges.apis.exchangeProduct.basePath}")
public interface ExchangeProductClient {

    @GetMapping(value = "${exchanges.apis.exchangeProduct.findByExchangeId}")
    List<ExchangeProductDTO> findByExchangeId(@PathVariable("exchangeEnum") ExchangeEnum exchangeEnum);
}
