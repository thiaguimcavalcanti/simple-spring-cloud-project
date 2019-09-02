package com.bot.exchanges.commons.repository;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.Candlestick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickRepository extends JpaRepository<Candlestick, String>, CustomCandlestickRepository {

    Candlestick findTopByExchangeProductIdAndPeriodEnumOrderByBeginTimeDesc(Long exchangeProductId,
                                                                            PeriodEnum periodEnum);
}