package com.bot.exchanges.commons.repository;

import com.bot.commons.enums.OrderStatusEnum;
import com.bot.exchanges.commons.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomOrderHistoryRepository {

    List<OrderHistory> findByExchangeIdAndStatusIn(Long exchangeId, List<OrderStatusEnum> orderStatusEnums);
}