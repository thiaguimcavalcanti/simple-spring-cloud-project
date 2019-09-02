package com.bot.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeProductDTO {
    private Long exchangeId;
    private String productId;
    private String baseProductId;
    private boolean active;
}
