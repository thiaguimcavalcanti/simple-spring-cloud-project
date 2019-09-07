package com.bot.exchanges;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.dto.OpenOrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.alphavantage.service.AlphaVantageService;
import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.bittrex.service.BittrexService;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.service.ExchangeService;
import com.bot.exchanges.cryptocompare.service.CryptoCompareService;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangesApiFacade {

    private final BittrexService bittrexService;
    private final BinanceService binanceService;
    private final CryptoCompareService cryptoCompareService;
    private final AlphaVantageService alphaVantageService;
    private final ExchangeProductRepository exchangeProductRepository;

    private ModelMapper mapper;

    @Autowired
    public ExchangesApiFacade(BittrexService bittrexService,
                              BinanceService binanceService,
                              CryptoCompareService cryptoCompareService,
                              AlphaVantageService alphaVantageService,
                              ExchangeProductRepository exchangeProductRepository,
                              ModelMapper mapper) {
        this.bittrexService = bittrexService;
        this.binanceService = binanceService;
        this.cryptoCompareService = cryptoCompareService;
        this.alphaVantageService = alphaVantageService;
        this.exchangeProductRepository = exchangeProductRepository;
        this.mapper = mapper;
    }

    public TickerDTO getTicker(ExchangeEnum exchangeEnum, String market) {
        return getExchangeServiceByType(exchangeEnum).getTicker(market);
    }

    public List<? extends BalanceDTO> getBalances(ExchangeEnum exchangeEnum, String userId) {
        return getExchangeServiceByType(exchangeEnum).getBalances(userId);
    }

    public List<? extends OpenOrderDTO> getOpenOrders(ExchangeEnum exchangeEnum, String baseProductId,
                                                      String productId, String userId) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        return getExchangeServiceByType(exchangeEnum).getOpenOrders(userId, exchangeProduct);
    }

    public List<? extends OrderHistoryDTO> getOrderHistory(ExchangeEnum exchangeEnum, String baseProductId,
                                                           String productId, String userId) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        return getExchangeServiceByType(exchangeEnum).getOrderHistory(userId, exchangeProduct);
    }

    public List<? extends CandlestickDTO> getCandlesticks(ExchangeEnum exchangeEnum, String baseProductId,
                                                          String productId, PeriodEnum periodEnum) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        return getExchangeServiceByType(exchangeEnum).getCandlesticks(exchangeProduct, periodEnum);
    }

    public List<? extends CandlestickDTO> refreshCandlestick(ExchangeEnum exchangeEnum, String baseProductId,
                                                             String productId, PeriodEnum periodEnum) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        return getExchangeServiceByType(exchangeEnum).refreshCandlestick(exchangeProduct, periodEnum);
    }

    public void refreshLatestCandlestick(ExchangeEnum exchangeEnum, String baseProductId,
                                                                       String productId, PeriodEnum periodEnum) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        getExchangeServiceByType(exchangeEnum).refreshLatestCandlestick(exchangeProduct, periodEnum);
    }

    private ExchangeProduct getExchangeProduct(ExchangeEnum exchangeEnum, String baseProductId, String productId) {
        baseProductId = StringUtils.isBlank(baseProductId) ? null : baseProductId;
        productId = StringUtils.isBlank(productId) ? null : productId;
        return exchangeProductRepository.findByExchangeIdAndBaseProductIdAndProductId(exchangeEnum.getId(),
                baseProductId, productId);
    }

    public void refreshProductList() {
        cryptoCompareService.refreshProductList();
        binanceService.refreshExchangeProductList();
        bittrexService.refreshExchangeProductList();
    }

    private ExchangeService getExchangeServiceByType(ExchangeEnum exchangeEnum) {
        switch (exchangeEnum) {
            case BITTREX:
                return bittrexService;
            case BINANCE:
                return binanceService;
            case BOVESPA:
                return alphaVantageService;
            default:
                return null;
        }
    }
}
