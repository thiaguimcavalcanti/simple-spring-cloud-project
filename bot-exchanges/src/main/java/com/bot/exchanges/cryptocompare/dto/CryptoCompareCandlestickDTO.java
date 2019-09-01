package com.bot.exchanges.cryptocompare.dto;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CryptoCompareCandlestickDTO extends CandlestickDTO {

  @Override
  @JsonSetter("open")
  public void setOpen(String open) {
    super.setOpen(open);
  }

  @Override
  @JsonSetter("high")
  public void setHigh(String high) {
    super.setHigh(high);
  }

  @Override
  @JsonSetter("low")
  public void setLow(String low) {
    super.setLow(low);
  }

  @Override
  @JsonSetter("close")
  public void setClose(String close) {
    super.setClose(close);
  }

  @Override
  @JsonSetter("volumeto")
  public void setVolume(String volume) {
    super.setVolume(volume);
  }

  @Override
  @JsonSetter("time")
  public void setCloseTime(String closeTime) {
    super.setCloseTime(closeTime);
  }
}