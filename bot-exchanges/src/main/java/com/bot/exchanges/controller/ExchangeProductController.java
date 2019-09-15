package com.bot.exchanges.controller;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.service.ExchangeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bot.commons.enums.ExchangeEnum.BINANCE;
import static com.bot.commons.enums.ExchangeEnum.BITTREX;

@RestController
@RequestMapping("/exchangeProduct")
public class ExchangeProductController {

    private ExchangeProductService exchangeProductService;
    private ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public ExchangeProductController(ExchangeProductService exchangeProductService,
                                     ExchangesApiFacade exchangesApiFacade) {
        this.exchangeProductService = exchangeProductService;
        this.exchangesApiFacade = exchangesApiFacade;
    }

    @GetMapping("/findByExchangeId")
    public ResponseEntity<List<ExchangeProduct>> findByExchangeId(@RequestParam ExchangeEnum exchangeEnum) {
        List<ExchangeProduct> exchangeProducts = exchangeProductService.findByExchangeId(exchangeEnum.getId());
        return ResponseEntity.ok(exchangeProducts);
    }

    @GetMapping("/refreshAll")
    public void refreshAll() {
        exchangesApiFacade.refreshProductList();
        exchangeProductService.refreshAll(BITTREX);
        exchangeProductService.refreshAll(BINANCE);
    }
}
