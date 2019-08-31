package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.StrategyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRuleRepository extends JpaRepository<StrategyRule, Long>, CustomStrategyRuleRepository {

}