package com.bot.exchanges.controller;

import com.bot.exchanges.ExchangesApiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public ProductController(ExchangesApiFacade exchangesApiFacade) {
        this.exchangesApiFacade = exchangesApiFacade;
    }

    @GetMapping("/refreshProductList")
    public void refreshProductList() {
        exchangesApiFacade.refreshProductList();
    }
}
