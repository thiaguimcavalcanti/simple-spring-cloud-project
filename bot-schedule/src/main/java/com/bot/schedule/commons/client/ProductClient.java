package com.bot.schedule.commons.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product-client", url = "${exchanges.baseUrl}${exchanges.apis.product.basePath}")
public interface ProductClient {

    @GetMapping(value = "${exchanges.apis.product.refreshProductList}")
    void refreshProductList();
}
