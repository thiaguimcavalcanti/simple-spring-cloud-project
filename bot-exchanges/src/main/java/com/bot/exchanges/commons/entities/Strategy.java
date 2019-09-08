package com.bot.exchanges.commons.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Strategy")
public class Strategy extends AbstractLongIDEntity {

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

	@Column(name = "Name")
	private String name;

	@NotNull
	@Column(name = "Active", nullable = false)
	private Boolean active = Boolean.FALSE;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "strategy")
	private List<StrategyRule> strategyRules;
}
