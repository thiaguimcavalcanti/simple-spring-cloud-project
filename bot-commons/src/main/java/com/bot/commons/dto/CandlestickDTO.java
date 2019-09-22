package com.bot.commons.dto;

import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandlestickDTO {
  private String symbol;
  private String baseProduct;
  private String product;
  private PeriodEnum periodEnum;
  private ZonedDateTime beginTime;
  private ZonedDateTime endTime;
  private CustomBigDecimal openPrice;
  private CustomBigDecimal closePrice;
  private CustomBigDecimal maxPrice;
  private CustomBigDecimal minPrice;
  private CustomBigDecimal volume;
}