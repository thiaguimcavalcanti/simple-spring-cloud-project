package com.bot.commons.dto;

import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import static com.bot.commons.utils.CommonConstants.MIN_PRICE;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeProductDTO {
    private Long exchangeId;
    private String productId;
    private String baseProductId;
    private boolean active;
    private CustomBigDecimal minPrice = MIN_PRICE;
    private CustomBigDecimal maxPrice;
    private CustomBigDecimal tickSize = MIN_PRICE;
    private CustomBigDecimal minQty = MIN_PRICE;
    private CustomBigDecimal maxQty;
    private CustomBigDecimal qtySize = MIN_PRICE;
    private CustomBigDecimal minTotal = MIN_PRICE;
}
