package com.bot.commons.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OpenOrderDTO {
    private String uuid;
    private String market;
    private String orderType;
    private BigDecimal quantity;
    private BigDecimal limit;
    private BigDecimal price;
    private BigDecimal pricePerUnit;
    private LocalDateTime opened;
}
