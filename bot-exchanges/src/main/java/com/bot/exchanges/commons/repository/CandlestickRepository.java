package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.enums.PeriodEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickRepository extends JpaRepository<Candlestick, String> {

    Candlestick findTopByExchangeProductIdAndPeriodEnumOrderByBeginTimeDesc(Long exchangeProductId,
                                                                            PeriodEnum periodEnum);

}