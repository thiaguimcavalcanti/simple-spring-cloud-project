package com.bot.exchanges.cryptocompare.dto;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CryptoCompareCandlestickDTO extends CandlestickDTO {

  @Override
  @JsonSetter("open")
  public void setOpenPrice(String openPrice) {
    super.setOpenPrice(openPrice);
  }

  @Override
  @JsonSetter("high")
  public void setMaxPrice(String maxPrice) {
    super.setMaxPrice(maxPrice);
  }

  @Override
  @JsonSetter("low")
  public void setMinPrice(String minPrice) {
    super.setMinPrice(minPrice);
  }

  @Override
  @JsonSetter("close")
  public void setClosePrice(String closePrice) {
    super.setClosePrice(closePrice);
  }

  @Override
  @JsonSetter("volumeto")
  public void setVolume(String volume) {
    super.setVolume(volume);
  }

  @Override
  @JsonSetter("time")
  public void setEndTime(String endTime) {
    super.setEndTime(endTime);
  }
}