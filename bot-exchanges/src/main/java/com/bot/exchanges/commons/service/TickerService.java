package com.bot.exchanges.commons.service;

import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;

import java.util.List;

public interface TickerService {

    void refreshAll(ExchangeEnum exchangeEnum, List<? extends TickerDTO> tickerDTO);
}
