package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long>, CustomStrategyRepository {

}