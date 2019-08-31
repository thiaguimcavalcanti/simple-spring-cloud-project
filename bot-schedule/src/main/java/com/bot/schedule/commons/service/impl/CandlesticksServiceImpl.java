package com.bot.schedule.commons.service.impl;

import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.schedule.commons.service.CandlestickService;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

@Service
public class CandlesticksServiceImpl implements CandlestickService {

    private ServletContext servletContext;

    private final ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public CandlesticksServiceImpl(ExchangesApiFacade exchangesApiFacade,
                                   ServletContext servletContext) {
        this.exchangesApiFacade = exchangesApiFacade;
        this.servletContext = servletContext;
    }

    @Override
    public void refreshLatestCandlestick(ExchangeEnum exchangeEnum, PeriodEnum periodEnum) {
        ExchangeSessionHelper exchangeSessionHelper = ExchangeSessionHelper.getInstance(servletContext);
        ExchangeProduct exchangeProduct = exchangeSessionHelper.getFirstExchangeProductToRefreshLatestCandlestick(exchangeEnum, periodEnum);

        if (exchangeProduct != null) {
            Candlestick latestCandlestick = exchangesApiFacade.refreshLatestCandlestick(exchangeEnum, exchangeProduct, periodEnum);

            // If a new candlestick was found, we added this Exchange Product to start the technical analysis
            if (latestCandlestick != null) {
                exchangeSessionHelper.addExchangeProductToStrategyAnalysis(exchangeEnum, periodEnum, exchangeProduct);
            }
        }
    }
}
