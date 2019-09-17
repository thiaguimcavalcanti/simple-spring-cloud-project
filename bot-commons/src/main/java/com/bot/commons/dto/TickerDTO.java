package com.bot.commons.dto;

import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class TickerDTO {
    private String baseProduct;
    private String product;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal bid;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal ask;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal last;
}
