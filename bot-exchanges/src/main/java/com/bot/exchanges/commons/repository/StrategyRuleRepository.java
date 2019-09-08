package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.StrategyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StrategyRuleRepository extends JpaRepository<StrategyRule, Long>, CustomStrategyRuleRepository {

    @Transactional
    @Modifying
    @Query("update StrategyRule s set s.buyActive = :buyActive, s.buySatisfied = false WHERE s.strategyId = :strategyId")
    void setBuyActiveByStrategyId(@Param("buyActive") Boolean buyActive, @Param("strategyId") Long strategyId);

    @Transactional
    @Modifying
    @Query("update StrategyRule s set s.sellActive = :sellActive, s.sellSatisfied = false WHERE s.strategyId = :strategyId")
    void setSellActiveByStrategyId(@Param("sellActive") Boolean sellActive, @Param("strategyId") Long strategyId);
}