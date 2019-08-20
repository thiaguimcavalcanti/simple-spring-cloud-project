package com.bot.exchanges.bittrex.dto.publicapi;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BittrexCandlestickDTO extends CandlestickDTO {

  @Override
  @JsonSetter("O")
  public void setOpen(String open) {
    super.setOpen(open);
  }

  @Override
  @JsonSetter("H")
  public void setHigh(String high) {
    super.setHigh(high);
  }

  @Override
  @JsonSetter("L")
  public void setLow(String low) {
    super.setLow(low);
  }

  @Override
  @JsonSetter("C")
  public void setClose(String close) {
    super.setClose(close);
  }

  @Override
  @JsonSetter("V")
  public void setVolume(String volume) {
    super.setVolume(volume);
  }

  @Override
  @JsonSetter("T")
  public void setCloseTime(String closeTime) {
    super.setCloseTime(closeTime);
  }
}