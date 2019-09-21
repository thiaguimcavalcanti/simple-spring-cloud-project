package com.bot.exchanges.commons.repository;

import com.bot.commons.enums.OrderStatusEnum;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.Ticker;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomTickerRepository {

    List<Ticker> findByExchangeId(Long exchangeId);
}