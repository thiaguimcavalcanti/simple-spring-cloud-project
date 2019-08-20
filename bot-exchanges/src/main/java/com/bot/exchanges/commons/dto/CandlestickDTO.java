package com.bot.exchanges.commons.dto;

import lombok.Data;

@Data
public class CandlestickDTO {
  private String openTime;
  private String closeTime;
  private String open;
  private String high;
  private String low;
  private String close;
  private String volume;
}