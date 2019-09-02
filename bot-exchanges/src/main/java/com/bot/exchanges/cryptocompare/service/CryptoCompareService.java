package com.bot.exchanges.cryptocompare.service;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.entities.ExchangeProduct;

public interface CryptoCompareService {

    void refreshProductList();

    CandlestickDTO getLatestCandlestick(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct, PeriodEnum periodEnum);
}
