package com.bot.exchanges.cryptocompare.dto;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCompareCandlestickDTO extends CandlestickDTO {

  @JsonCreator
  public CryptoCompareCandlestickDTO(@JsonProperty("open") String open, @JsonProperty("high") String high,
                               @JsonProperty("low") String low, @JsonProperty("close") String close,
                               @JsonProperty("volumeto") String volume, @JsonProperty("time") String closeTime) {
    setOpenPrice(CustomBigDecimal.valueOf(open));
    setClosePrice(CustomBigDecimal.valueOf(close));
    setVolume(CustomBigDecimal.valueOf(volume));
    setMaxPrice(CustomBigDecimal.valueOf(high));
    setMinPrice(CustomBigDecimal.valueOf(low));
    setEndTime(DateUtils.convertEpochSecondToZonedDateTime(closeTime));
  }
}