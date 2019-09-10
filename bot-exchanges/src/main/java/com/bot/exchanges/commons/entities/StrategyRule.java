package com.bot.exchanges.commons.entities;

import com.bot.commons.enums.PeriodEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "StrategyRule")
public class StrategyRule extends AbstractLongIDEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Strategy_ID", referencedColumnName = "id")
	private Strategy strategy;

	@Column(name = "Strategy_ID", insertable = false, updatable = false)
	private Long strategyId;

	/** Time period (e.g. 1 day, 15 min, etc.) of the bar */
	@Enumerated(EnumType.STRING)
	@Column(name = "TimePeriod")
	private PeriodEnum periodEnum;

	@Type(type="text")
	@Column(name = "Buy")
	private String buy;

	@Column(name = "BuySatisfied")
	private Boolean buySatisfied = Boolean.FALSE;

	@Column(name = "BuyActive")
	private Boolean buyActive = Boolean.FALSE;

	@Type(type="text")
	@Column(name = "Sell")
	private String sell;

	@Column(name = "SellActive")
	private Boolean sellActive = Boolean.FALSE;

	@Column(name = "SellSatisfied")
	private Boolean sellSatisfied = Boolean.FALSE;
}
