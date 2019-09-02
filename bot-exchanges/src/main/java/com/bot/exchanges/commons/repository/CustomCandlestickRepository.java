package com.bot.exchanges.commons.repository;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CustomCandlestickRepository {

    List<Candlestick> getToAnalyze(ExchangeProduct exchangeProduct, ZonedDateTime beginTime, ZonedDateTime endTime, PeriodEnum periodEnum);
}