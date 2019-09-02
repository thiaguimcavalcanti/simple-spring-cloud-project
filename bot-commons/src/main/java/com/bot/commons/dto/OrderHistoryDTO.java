package com.bot.commons.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderHistoryDTO {
    private String uuid;
    private String market;
    private LocalDateTime originalDate;
    private String orderType;
    private BigDecimal limit;
    private BigDecimal quantity;
    private BigDecimal quantityRemaining;
    private BigDecimal price;
    private BigDecimal pricePerUnit;
}
