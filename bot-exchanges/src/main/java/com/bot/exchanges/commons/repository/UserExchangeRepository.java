package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.UserExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExchangeRepository extends JpaRepository<UserExchange, String> {

    UserExchange findByUserIdAndExchangeId(String userId, Long exchangeId);
}