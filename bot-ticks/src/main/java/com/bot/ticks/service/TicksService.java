package com.bot.ticks.service;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.TickerDTO;

import java.util.List;

public interface TicksService {

    TickerDTO getTicker(String market);

    String ticks();

    List<BalanceDTO> getBalances();
}
