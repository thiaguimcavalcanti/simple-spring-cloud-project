package com.bot.exchanges.repository;

import com.bot.exchanges.commons.entities.Candlestick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickRepository extends JpaRepository<Candlestick, String> {

}