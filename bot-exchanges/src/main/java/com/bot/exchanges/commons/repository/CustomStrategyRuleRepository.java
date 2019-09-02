package com.bot.exchanges.commons.repository;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.StrategyRule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomStrategyRuleRepository {

    List<StrategyRule> findByExchangeProductAndPeriodEnum(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);
}