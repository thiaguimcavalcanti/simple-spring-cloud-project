package com.bot.exchanges.commons.entities;

import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.types.CustomBigDecimalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.ZonedDateTime;

import static com.bot.exchanges.commons.utils.CommonConstants.CUSTOMBIGDECIMAL_DATA_TYPE;

@Getter
@Setter
@Entity
@Table(name = "Ticker", uniqueConstraints = {@UniqueConstraint(columnNames = {"ExchangeProduct_ID"}, name = "uk_exchangeProduct")})
@TypeDef(name = CUSTOMBIGDECIMAL_DATA_TYPE, defaultForType = CustomBigDecimal.class, typeClass = CustomBigDecimalType.class)
public class Ticker extends AbstractLongIDEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ExchangeProduct_ID", referencedColumnName = "id")
	private ExchangeProduct exchangeProduct;

	@Column(name = "ExchangeProduct_ID", insertable = false, updatable = false)
	private Long exchangeProductId;

	@Column(name = "Volume", precision = 20, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal volume;

	@Column(name = "LastUpdate")
	private ZonedDateTime lastUpdate;

	@Column(name = "ChangePercent", precision = 7, scale = 2)
	private Double changePercent;

	@Column(name = "High", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal high;

	@Column(name = "Low", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal low;

	@Column(name = "Last", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal last;

	@Column(name = "Ask", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal ask;

	@Column(name = "Bid", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal bid;

	@Column(name = "PrevDay", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal prevDay;

	@Column(name = "OpenBuyOrders")
	private int openBuyOrders;

	@Column(name = "OpenSellOrders")
	private int openSellOrders;
}
