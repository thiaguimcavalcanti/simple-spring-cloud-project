package com.bot.exchanges.commons.repository;

import com.bot.commons.dto.TickerDTO;
import com.bot.exchanges.commons.entities.MarketSummary;
import com.bot.exchanges.commons.entities.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long>, CustomTickerRepository {

    Ticker findByExchangeProductId(Long id);
}