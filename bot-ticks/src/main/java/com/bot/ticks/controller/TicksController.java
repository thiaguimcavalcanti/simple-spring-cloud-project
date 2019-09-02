package com.bot.ticks.controller;

import com.bot.ticks.service.TicksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicksController {

    @Value("${msg:Nothing}")
    private String msg;

    @Value("${foo:Nothing}")
    private String foo;

    private final TicksService ticksService;

    @Autowired
    public TicksController(TicksService ticksService) {
        this.ticksService = ticksService;
    }

    @GetMapping("/ticks")
    public String ticks() {
        return ticksService.ticks();
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
