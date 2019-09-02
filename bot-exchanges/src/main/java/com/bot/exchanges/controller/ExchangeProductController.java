package com.bot.exchanges.controller;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchangeProduct")
public class ExchangeProductController {

    private ExchangeProductRepository exchangeProductRepository;

    @Autowired
    public ExchangeProductController(ExchangeProductRepository exchangeProductRepository) {
        this.exchangeProductRepository = exchangeProductRepository;
    }

    @GetMapping("/findByExchangeId")
    public ResponseEntity<List<ExchangeProduct>> findByExchangeId(@RequestParam ExchangeEnum exchangeEnum) {
        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeEnum.getId());
        return ResponseEntity.ok(exchangeProducts);
    }
}
