package com.bot.exchanges.bittrex.dto.publicapi;

import static com.bot.commons.utils.DateUtils.DATETIME_WITH_T_FORMAT_DESERIALIZE;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexCandlestickDTO extends CandlestickDTO {

  @JsonCreator
  public BittrexCandlestickDTO(@JsonProperty("O") String open, @JsonProperty("H") String high,
                               @JsonProperty("L") String low, @JsonProperty("C") String close,
                               @JsonProperty("V") String volume, @JsonProperty("T") String closeTime) {
    setOpenPrice(CustomBigDecimal.valueOf(open));
    setClosePrice(CustomBigDecimal.valueOf(close));
    setVolume(CustomBigDecimal.valueOf(volume));
    setMaxPrice(CustomBigDecimal.valueOf(high));
    setMinPrice(CustomBigDecimal.valueOf(low));
    setEndTime(DateUtils.convertDateToZonedDateTime(closeTime, DATETIME_WITH_T_FORMAT_DESERIALIZE));
  }
}