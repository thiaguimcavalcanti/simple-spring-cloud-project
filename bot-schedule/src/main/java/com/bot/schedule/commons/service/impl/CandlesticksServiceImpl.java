package com.bot.schedule.commons.service.impl;

import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.schedule.commons.client.CandlestickClient;
import com.bot.schedule.commons.service.CandlestickService;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

@Service
public class CandlesticksServiceImpl implements CandlestickService {

    private ServletContext servletContext;

    private final CandlestickClient candlesticksClient;

    @Autowired
    public CandlesticksServiceImpl(CandlestickClient candlesticksClient,
                                   ServletContext servletContext) {
        this.candlesticksClient = candlesticksClient;
        this.servletContext = servletContext;
    }

    @Override
    public void refreshLatestCandlestick(ExchangeEnum exchangeEnum, PeriodEnum periodEnum) {
        ExchangeSessionHelper exchangeSessionHelper = ExchangeSessionHelper.getInstance(servletContext);
        ExchangeProductDTO exchangeProduct = exchangeSessionHelper.getFirstExchangeProductToRefreshLatestCandlestick(exchangeEnum, periodEnum);

        if (exchangeProduct != null) {
            candlesticksClient.refreshLatestCandlestick(exchangeEnum, exchangeProduct.getBaseProductId(),
                    exchangeProduct.getProductId(), periodEnum);
        }
    }
}
