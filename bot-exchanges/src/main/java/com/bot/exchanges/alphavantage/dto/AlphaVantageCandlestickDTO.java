package com.bot.exchanges.alphavantage.dto;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AlphaVantageCandlestickDTO extends CandlestickDTO {

  @JsonCreator
  public AlphaVantageCandlestickDTO(Map<String, String> properties) {
    setOpenPrice(CustomBigDecimal.valueOf(properties.get("1. open")));
    setMaxPrice(CustomBigDecimal.valueOf(properties.get("2. high")));
    setMinPrice(CustomBigDecimal.valueOf(properties.get("3. low")));
    setClosePrice(CustomBigDecimal.valueOf(properties.get("4. close")));
    setVolume(CustomBigDecimal.valueOf(properties.get("5. volume")));
  }
}