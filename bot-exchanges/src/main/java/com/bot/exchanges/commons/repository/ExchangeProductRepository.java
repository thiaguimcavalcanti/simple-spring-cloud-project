package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeProductRepository extends JpaRepository<ExchangeProduct, String> {

    List<ExchangeProduct> findByExchangeId(Long exchangeId);

}