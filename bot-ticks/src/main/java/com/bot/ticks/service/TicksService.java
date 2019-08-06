package com.bot.ticks.service;

import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.TickerDTO;

import java.util.List;

public interface TicksService {

    TickerDTO getTicker(String market);

    String ticks();

    List<BalanceDTO> getBalances();
}
