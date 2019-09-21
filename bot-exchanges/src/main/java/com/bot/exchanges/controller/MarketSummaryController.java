package com.bot.exchanges.controller;

import com.bot.commons.dto.BaseListDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.service.MarketSummaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marketSummary")
public class MarketSummaryController {

    private MarketSummaryService marketSummaryService;
    private ModelMapper mapper;

    @Autowired
    public MarketSummaryController(MarketSummaryService marketSummaryService, ModelMapper mapper) {
        this.marketSummaryService = marketSummaryService;
        this.mapper = mapper;
    }

    @PostMapping("/refreshAll")
    public void refreshAll(@RequestParam ExchangeEnum exchangeEnum,
                           @RequestBody BaseListDTO<? extends MarketSummaryDTO> marketSummariesDTO) {
        marketSummaryService.refreshMarketSummaries(exchangeEnum, marketSummariesDTO.getData());
    }

    @GetMapping("/refreshAll")
    public void refreshAll(@RequestParam ExchangeEnum exchangeEnum) {
        marketSummaryService.refreshMarketSummaries(exchangeEnum, (ExchangeProduct) null);
    }
}
