package com.bot.exchanges.bittrex.dto.publicapi;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BittrexCandlestickDTO extends CandlestickDTO {

  @Override
  @JsonSetter("O")
  public void setOpenPrice(String openPrice) {
    super.setOpenPrice(openPrice);
  }

  @Override
  @JsonSetter("H")
  public void setMaxPrice(String maxPrice) {
    super.setMaxPrice(maxPrice);
  }

  @Override
  @JsonSetter("L")
  public void setMinPrice(String minPrice) {
    super.setMinPrice(minPrice);
  }

  @Override
  @JsonSetter("C")
  public void setClosePrice(String closePrice) {
    super.setClosePrice(closePrice);
  }

  @Override
  @JsonSetter("V")
  public void setVolume(String volume) {
    super.setVolume(volume);
  }

  @Override
  @JsonSetter("T")
  public void setEndTime(String endTime) {
    super.setEndTime(endTime);
  }
}