package com.bot.ticks.controller;

import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import com.bot.ticks.service.TicksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicksController {

    @Value("${msg:Nothing}")
    private String msg;

    @Value("${foo:Nothing}")
    private String foo;

    @Autowired
    private TicksService ticksService;

    @GetMapping("/ticks")
    public String ticks() {
        return ticksService.ticks();
    }

    @GetMapping("/ticker/{market}")
    public ResponseEntity<TickerDTO> ticker(@PathVariable("market") String market) {
        return ResponseEntity.ok(ticksService.getTicker(market));
    }

    @GetMapping("/balances")
    public ResponseEntity<List<BalanceDTO>> balances() {
        return ResponseEntity.ok(ticksService.getBalances());
    }

    @GetMapping("/msg")
    public String getMsg() {
        return msg;
    }

    @GetMapping("/foo")
    public String getFoo() {
        return foo;
    }
}
