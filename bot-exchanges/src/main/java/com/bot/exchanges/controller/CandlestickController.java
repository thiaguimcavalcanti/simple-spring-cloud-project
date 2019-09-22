package com.bot.exchanges.controller;

import com.bot.commons.dto.BaseListDTO;
import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.service.CandlestickService;
import com.bot.exchanges.commons.service.ExchangeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candlestick")
public class CandlestickController {

    private CandlestickService candlestickService;
    private ExchangeProductService exchangeProductService;

    @Autowired
    public CandlestickController(CandlestickService candlestickService,
                                 ExchangeProductService exchangeProductService) {
        this.candlestickService = candlestickService;
        this.exchangeProductService = exchangeProductService;
    }

    @GetMapping("/refreshLatestCandlestick")
    public void refreshLatestCandlestick(@RequestParam ExchangeEnum exchangeEnum,
                                         @RequestParam(required = false) String baseProductId,
                                         @RequestParam String productId,
                                         @RequestParam PeriodEnum periodEnum) {
        ExchangeProduct exchangeProduct = exchangeProductService.getExchangeProduct(exchangeEnum, baseProductId,
                productId);
        candlestickService.refreshLatestCandlestick(exchangeProduct, periodEnum);
    }

    @PostMapping("/refreshLatestCandlesticks")
    public void refreshLatestCandlesticks(@RequestParam ExchangeEnum exchangeEnum,
                                          @RequestBody BaseListDTO<? extends CandlestickDTO> candlesticksDTO) {
        for (CandlestickDTO candlestickDTO : candlesticksDTO.getData()) {
            ExchangeProduct exchangeProduct = exchangeProductService.getExchangeProduct(exchangeEnum,
                    candlestickDTO.getBaseProduct(), candlestickDTO.getProduct());
            if (exchangeProduct != null) {
                candlestickService.refreshLatestCandlestick(exchangeProduct, candlestickDTO.getPeriodEnum(), candlestickDTO);
            }
        }
    }
}
