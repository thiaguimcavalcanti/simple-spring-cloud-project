package com.bot.exchanges.commons.repository;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.BackTesting;
import com.bot.exchanges.commons.entities.Candlestick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackTestingRepository extends JpaRepository<BackTesting, Long> {

}