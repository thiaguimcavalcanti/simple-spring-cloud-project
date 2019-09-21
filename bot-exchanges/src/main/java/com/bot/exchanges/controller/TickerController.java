package com.bot.exchanges.controller;

import com.bot.commons.dto.BaseListDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.service.TickerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticker")
public class TickerController {

    private TickerService tickerService;
    private ModelMapper mapper;

    @Autowired
    public TickerController(TickerService tickerService, ModelMapper mapper) {
        this.tickerService = tickerService;
        this.mapper = mapper;
    }

    @PostMapping("/refreshAll")
    public void refreshAll(@RequestParam ExchangeEnum exchangeEnum,
                           @RequestBody BaseListDTO<TickerDTO> tickerDTO) {
        tickerService.refreshAll(exchangeEnum, tickerDTO.getData());
    }
}
