package com.bot.exchanges.controller;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.ExchangesApiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candlestick")
public class CandlestickController {

    private ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public CandlestickController(ExchangesApiFacade exchangesApiFacade) {
        this.exchangesApiFacade = exchangesApiFacade;
    }

    @GetMapping("/refreshLatestCandlestick")
    public void refreshLatestCandlestick(@RequestParam ExchangeEnum exchangeEnum,
                                         @RequestParam(required = false) String baseProductId,
                                         @RequestParam String productId,
                                         @RequestParam PeriodEnum periodEnum) {
        exchangesApiFacade.refreshLatestCandlestick(exchangeEnum, baseProductId, productId, periodEnum);
    }
}
