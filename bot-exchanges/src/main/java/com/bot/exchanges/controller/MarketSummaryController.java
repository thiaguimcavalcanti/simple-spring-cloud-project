package com.bot.exchanges.controller;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.ExchangesApiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marketSummary")
public class MarketSummaryController {

    private ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public MarketSummaryController(ExchangesApiFacade exchangesApiFacade) {
        this.exchangesApiFacade = exchangesApiFacade;
    }

    @GetMapping("/refreshAll")
    public void refreshAll(@RequestParam ExchangeEnum exchangeEnum) {
        exchangesApiFacade.refreshMarketSummaries(exchangeEnum);
    }
}
