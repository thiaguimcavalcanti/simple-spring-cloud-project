package com.bot.exchanges.cryptocompare.service;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;

public interface CryptoCompareService {

    void refreshProductList();

    CandlestickDTO getLatestCandlestick(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct, PeriodEnum periodEnum);
}
