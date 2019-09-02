package com.bot.exchanges;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.OpenOrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.bittrex.service.BittrexService;
import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.service.ExchangeService;
import com.bot.exchanges.cryptocompare.service.CryptoCompareService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangesApiFacade {

    private final BittrexService bittrexService;
    private final BinanceService binanceService;
    private final CryptoCompareService cryptoCompareService;
    private final ExchangeProductRepository exchangeProductRepository;

    private ModelMapper mapper;

    @Autowired
    public ExchangesApiFacade(BittrexService bittrexService,
                              BinanceService binanceService,
                              CryptoCompareService cryptoCompareService,
                              ExchangeProductRepository exchangeProductRepository,
                              ModelMapper mapper) {
        this.bittrexService = bittrexService;
        this.binanceService = binanceService;
        this.cryptoCompareService = cryptoCompareService;
        this.exchangeProductRepository = exchangeProductRepository;
        this.mapper = mapper;
    }

    public TickerDTO getTicker(ExchangeEnum exchangeEnum, String market) {
        return getExchangeServiceByType(exchangeEnum).getTicker(market);
    }

    public List<? extends BalanceDTO> getBalances(ExchangeEnum exchangeEnum, String userId) {
        return getExchangeServiceByType(exchangeEnum).getBalances(userId);
    }

    public List<? extends OpenOrderDTO> getOpenOrders(ExchangeEnum exchangeEnum, String userId, String market) {
        return getExchangeServiceByType(exchangeEnum).getOpenOrders(userId, market);
    }

    public List<? extends OrderHistoryDTO> getOrderHistory(ExchangeEnum exchangeEnum, String userId, String market) {
        return getExchangeServiceByType(exchangeEnum).getOrderHistory(userId, market);
    }

    public List<? extends CandlestickDTO> getCandlesticks(ExchangeEnum exchangeEnum, String baseProductId, String productId,
                                                          PeriodEnum periodEnum) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        return getExchangeServiceByType(exchangeEnum).getCandlesticks(exchangeProduct, periodEnum);
    }

    public void refreshProductList() {
        cryptoCompareService.refreshProductList();
        binanceService.refreshExchangeProductList();
        bittrexService.refreshExchangeProductList();
    }

    public List<Candlestick> refreshCandlestick(ExchangeEnum exchangeEnum, String baseProductId, String productId,
                                                PeriodEnum periodEnum) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        return getExchangeServiceByType(exchangeEnum).refreshCandlestick(exchangeProduct, periodEnum);
    }

    public com.bot.commons.dto.CandlestickDTO refreshLatestCandlestick(ExchangeEnum exchangeEnum, String baseProductId,
                                                                       String productId, PeriodEnum periodEnum) {
        ExchangeProduct exchangeProduct = getExchangeProduct(exchangeEnum, baseProductId, productId);
        Candlestick candlestick = getExchangeServiceByType(exchangeEnum).refreshLatestCandlestick(exchangeProduct, periodEnum);
        return mapper.map(candlestick, com.bot.commons.dto.CandlestickDTO.class);
    }

    private ExchangeProduct getExchangeProduct(ExchangeEnum exchangeEnum, String baseProductId, String productId) {
        return exchangeProductRepository.findByExchangeIdAndBaseProductIdAndProductId(exchangeEnum.getId(),
                baseProductId, productId);
    }

    private ExchangeService getExchangeServiceByType(ExchangeEnum exchangeEnum) {
        switch (exchangeEnum) {
            case BITTREX:
                return bittrexService;
            case BINANCE:
                return binanceService;
            default:
                return null;
        }
    }
}
