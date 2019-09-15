package com.bot.exchanges.commons.service;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;

import java.util.List;

public interface ExchangeProductService {

    void refreshAll(ExchangeEnum exchangeEnum);

    ExchangeProduct getExchangeProduct(ExchangeEnum exchangeEnum, String baseProductId, String productId);

    List<ExchangeProduct> findByExchangeId(Long id);
}
