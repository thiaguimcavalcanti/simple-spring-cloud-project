package com.bot.exchanges.commons.dto;

import lombok.Data;

@Data
public class ExchangeProductDTO {
    private String productId;
    private String baseProductId;
    private boolean active;
}
