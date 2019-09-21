package com.bot.exchanges.commons.entities;

import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.types.CustomBigDecimalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.ta4j.core.Bar;
import org.ta4j.core.num.Num;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Duration;
import java.time.ZonedDateTime;

import static com.bot.exchanges.commons.utils.CommonConstants.CUSTOMBIGDECIMAL_DATA_TYPE;

@Getter
@Setter
@Entity
@Table(name = "Candlestick", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "TimePeriod", "BeginTime", "EndTime", "ExchangeProduct_ID" }) })
@TypeDef(name = CUSTOMBIGDECIMAL_DATA_TYPE, defaultForType = CustomBigDecimal.class, typeClass = CustomBigDecimalType.class)
public class Candlestick extends AbstractStringIDEntity implements Bar {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ExchangeProduct_ID", referencedColumnName = "id")
	private ExchangeProduct exchangeProduct;

	@Column(name = "ExchangeProduct_ID", insertable = false, updatable = false)
	private Long exchangeProductId;

	/** Time period (e.g. 1 day, 15 min, etc.) of the bar */
	@Enumerated(EnumType.STRING)
	@Column(name = "TimePeriod")
	private PeriodEnum periodEnum;

	/** Begin time of the bar */
	@Column(name = "BeginTime")
	private ZonedDateTime beginTime;

	/** End time of the bar */
	@Column(name = "EndTime")
	private ZonedDateTime endTime;

	/** Open price of the period */
	@Column(name = "OpenPrice", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal openPrice;

	/** Close price of the period */
	@Column(name = "ClosePrice", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal closePrice;

	/** Max price of the period */
	@Column(name = "MaxPrice", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal maxPrice;

	/** Min price of the period */
	@Column(name = "MinPrice", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal minPrice;

	/** Traded amount during the period */
	@Column(name = "Amount", precision = 20, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal amount;

	/** Volume of the period */
	@Column(name = "Volume", precision = 20, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal volume;

	@Override
	public int getTrades() {
		return -1;
	}

	@Override
	public Duration getTimePeriod() {
		return (Duration) periodEnum.getDuration();
	}

	@Override
	public void addTrade(Num tradeVolume, Num tradePrice) {
		// Do nothing
	}

	@Override
	public void addPrice(Num price) {
		// Do nothing
	}
}
