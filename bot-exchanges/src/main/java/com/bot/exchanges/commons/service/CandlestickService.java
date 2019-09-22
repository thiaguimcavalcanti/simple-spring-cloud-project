package com.bot.exchanges.commons.service;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.UserExchange;
import org.springframework.scheduling.annotation.Async;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface CandlestickService {

    void refreshLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);

    void refreshLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum,
                                  CandlestickDTO latestCandlestickDTO);

    @Async
    List<? extends CandlestickDTO> refreshCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum);
}
