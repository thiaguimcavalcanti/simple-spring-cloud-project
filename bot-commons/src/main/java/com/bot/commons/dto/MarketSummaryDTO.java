package com.bot.commons.dto;

import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class MarketSummaryDTO {
	private String symbol;
	private String baseProduct;
	private String product;
	private Double changePercent;
	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private CustomBigDecimal volume;
	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private CustomBigDecimal high;
	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private CustomBigDecimal low;
	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private CustomBigDecimal last;
	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private CustomBigDecimal ask;
	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private CustomBigDecimal bid;
	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private CustomBigDecimal prevDay;
}
