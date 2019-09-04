package com.bot.exchanges.controller;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/strategy")
public class StrategyController {

    private StrategyAnalysisService strategyAnalysisService;

    @Autowired
    public StrategyController(StrategyAnalysisService strategyAnalysisService) {
        this.strategyAnalysisService = strategyAnalysisService;
    }

    @GetMapping("/monitoring")
    public void monitoringStrategies(@RequestParam ExchangeEnum exchangeEnum) {
        strategyAnalysisService.monitoringStrategies(exchangeEnum);
    }
}
