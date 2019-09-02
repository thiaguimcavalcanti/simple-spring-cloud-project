package com.bot.exchanges.commons.dto;

import lombok.Data;

@Data
public class CandlestickDTO {
  private String beginTime;
  private String endTime;
  private String openPrice;
  private String maxPrice;
  private String minPrice;
  private String closePrice;
  private String volume;
}