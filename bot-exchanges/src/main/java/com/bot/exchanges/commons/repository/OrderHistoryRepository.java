package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    OrderHistory findTopByExchangeProductIdAndUserExchangeIdOrderByDateDescTypeDesc(Long exchangeProductId, Long userExchangeId);
}