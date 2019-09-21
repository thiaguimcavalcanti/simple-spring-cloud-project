package com.bot.exchanges.commons.entities;

import com.bot.commons.enums.OrderStatusEnum;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.utils.CommonConstants;
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
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.bot.exchanges.commons.utils.CommonConstants.CUSTOMBIGDECIMAL_DATA_TYPE;

@Getter
@Setter
@Entity
@Table(name = "OrderHistory")
public class OrderHistory extends AbstractLongIDEntity {

	@Column(name = "OrderId")
	private String orderId;

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

	@Column(name = "Price", precision = 14, scale = 8)
	@Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal price;

	@Column(name = "OriginalPrice", precision = 14, scale = 8)
	@Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal originalPrice;

	@Column(name = "Quantity", precision = 14, scale = 8)
	@Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal quantity;

	@Column(name = "Total", precision = 14, scale = 8)
	@Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal total = CustomBigDecimal.valueOf(0);

	@Column(name = "TotalWithFee", precision = 14, scale = 8)
	@Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal totalWithFee;

	@Column(name = "Fee", precision = 14, scale = 8)
	@Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal fee = CustomBigDecimal.valueOf(0);

	@Column(name = "OrderDate")
	private ZonedDateTime date;

	@Column(name = "OriginalOrderDate")
	private ZonedDateTime originalDate;

	@Column(name = "Simulation")
	private Boolean simulation = Boolean.FALSE;

	@Column(name = "Profit", precision = 7, scale = 2)
	@Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal profit;

	@Column(name = "ProfitWithFee", precision = 7, scale = 2)
	@Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
	private CustomBigDecimal profitWithFee;
}
