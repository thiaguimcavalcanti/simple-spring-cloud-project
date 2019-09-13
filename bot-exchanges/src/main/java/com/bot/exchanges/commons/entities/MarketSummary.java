package com.bot.exchanges.commons.entities;

import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.types.NumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.ta4j.core.num.Num;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "MarketSummary", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "ExchangeProduct_ID", "TickDate" }, name = "uk_exchangeProduct_tickDate") })
@TypeDef(name = "num_type", defaultForType = Num.class, typeClass = NumType.class)
public class MarketSummary extends AbstractLongIDEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ExchangeProduct_ID", referencedColumnName = "id")
	private ExchangeProduct exchangeProduct;

	@Column(name = "Volume", precision = 20, scale = 8)
	@Type(type = "num_type")
	private Num volume;

	@Column(name = "TickDate")
	private ZonedDateTime tickDate;

	@Column(name = "ChangePercent", precision = 7, scale = 2)
	private Double changePercent;

	@Column(name = "High", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num high;

	@Column(name = "Low", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num low;

	@Column(name = "Last", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num last;

	@Column(name = "Ask", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num ask;

	@Column(name = "Bid", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num bid;

	@Column(name = "PrevDay", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num prevDay;

	@Column(name = "OpenBuyOrders")
	private int openBuyOrders;

	@Column(name = "OpenSellOrders")
	private int openSellOrders;
}
