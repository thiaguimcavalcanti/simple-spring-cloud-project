package com.bot.exchanges.binance.dto.publicapi;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"openTime", "open", "high", "low", "close", "volume", "closeTime","quoteAssetVolume",
  "numberOfTrades", "takerBuyBaseAssetVolume", "takerBuyQuoteAssetVolume"})
public class BinanceCandlestickDTO extends CandlestickDTO {
  private String quoteAssetVolume;
  private Long numberOfTrades;
  private String takerBuyBaseAssetVolume;
  private String takerBuyQuoteAssetVolume;
}