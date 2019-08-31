package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.Strategy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomStrategyRepository {

    List<Strategy> findByActiveIsTrueAndExchangeId(Long exchangeId);
}