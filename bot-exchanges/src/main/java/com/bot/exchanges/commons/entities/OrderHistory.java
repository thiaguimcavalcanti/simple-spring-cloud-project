package com.bot.exchanges.commons.entities;

import com.bot.commons.enums.OrderStatusEnum;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.ta4j.core.num.Num;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "OrderHistory")
public class OrderHistory extends AbstractLongIDEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ExchangeProduct_ID", referencedColumnName = "id")
	private ExchangeProduct exchangeProduct;

	@Column(name = "ExchangeProduct_ID", insertable = false, updatable = false)
	private Long exchangeProductId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserExchange_ID", nullable = false, referencedColumnName = "id")
	private UserExchange userExchange;

	@Column(name = "UserExchange_ID", insertable = false, updatable = false)
	private Long userExchangeId;

	@Enumerated(EnumType.STRING)
	@Column(name = "OrderType")
	private OrderTypeEnum type;

	@Enumerated(EnumType.STRING)
	@Column(name = "OrderStatus")
	private OrderStatusEnum status;

	@Column(name = "Rate", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num rate;

	@Column(name = "OriginalRate", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num originalRate;

	@Column(name = "Amount", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num amount;

	@Column(name = "Total", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num total = CustomBigDecimal.valueOf(0);

	@Column(name = "TotalWithFee", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num totalWithFee;

	@Column(name = "Fee", precision = 14, scale = 8)
	@Type(type = "num_type")
	private Num fee = CustomBigDecimal.valueOf(0);

	@Column(name = "OrderDate")
	private ZonedDateTime date;

	@Column(name = "OriginalOrderDate")
	private ZonedDateTime originalDate;

	@Column(name = "Simulation")
	private Boolean simulation = Boolean.FALSE;

	@Column(name = "Profit", precision = 7, scale = 2)
	@Type(type = "num_type")
	private Num profit;

	@Column(name = "ProfitWithFee", precision = 7, scale = 2)
	@Type(type = "num_type")
	private Num profitWithFee;

	@Column(name = "number")
	private String number;
}
