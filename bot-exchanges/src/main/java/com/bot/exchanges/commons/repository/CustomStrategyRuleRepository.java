package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.StrategyRule;
import com.bot.exchanges.commons.enums.PeriodEnum;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomStrategyRuleRepository {

    List<StrategyRule> findByExchangeProductAndPeriodEnum(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);
}