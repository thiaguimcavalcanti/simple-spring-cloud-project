package com.bot.commons.dto;

import com.bot.commons.utils.NumDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.ta4j.core.num.Num;

import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandlestickDTO {
  private ZonedDateTime beginTime;
  private ZonedDateTime endTime;
  @JsonDeserialize(using = NumDeserializer.class)
  private Num openPrice;
  @JsonDeserialize(using = NumDeserializer.class)
  private Num closePrice;
  @JsonDeserialize(using = NumDeserializer.class)
  private Num maxPrice;
  @JsonDeserialize(using = NumDeserializer.class)
  private Num minPrice;
  @JsonDeserialize(using = NumDeserializer.class)
  private Num volume;
}