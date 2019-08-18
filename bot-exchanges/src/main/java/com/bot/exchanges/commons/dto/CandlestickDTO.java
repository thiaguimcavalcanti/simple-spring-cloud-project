package com.bot.exchanges.commons.dto;

import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Data
public class CandlestickDTO {

  private Long openTime;

  private Long closeTime;

  private String open;

  private String high;

  private String low;

  private String close;

  private String volume;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("openTime", openTime)
        .append("open", open)
        .append("high", high)
        .append("low", low)
        .append("close", close)
        .append("volume", volume)
        .append("closeTime", closeTime)
        .toString();
  }
}