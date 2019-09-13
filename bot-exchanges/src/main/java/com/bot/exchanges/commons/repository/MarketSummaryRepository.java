package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.MarketSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketSummaryRepository extends JpaRepository<MarketSummary, Long> {

}