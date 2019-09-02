package com.bot.exchanges.binance.dto.publicapi;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"openTime", "open", "high", "low", "close", "volume", "closeTime","quoteAssetVolume",
  "numberOfTrades", "takerBuyBaseAssetVolume", "takerBuyQuoteAssetVolume"})
public class BinanceCandlestickDTO extends CandlestickDTO {

  private String quoteAssetVolume;
  private Long numberOfTrades;
  private String takerBuyBaseAssetVolume;
  private String takerBuyQuoteAssetVolume;

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
  @JsonSetter("volume")
  public void setVolume(String volume) {
    super.setVolume(volume);
  }

  @Override
  @JsonSetter("openTime")
  public void setBeginTime(String beginTime) {
    super.setBeginTime(beginTime);
  }

  @Override
  @JsonSetter("closeTime")
  public void setEndTime(String endTime) {
    super.setEndTime(endTime);
  }
}