package com.bot.exchanges;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.dto.OpenOrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.alphavantage.AlphaVantageApiFacade;
import com.bot.exchanges.binance.BinanceApiFacade;
import com.bot.exchanges.bittrex.BittrexApiFacade;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.ExchangeApiFacade;
import com.bot.exchanges.cryptocompare.service.CryptoCompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangesApiFacade {

    private final BittrexApiFacade bittrexService;
    private final BinanceApiFacade binanceService;
    private final CryptoCompareService cryptoCompareService;
    private final AlphaVantageApiFacade alphaVantageService;

    @Autowired
    public ExchangesApiFacade(BittrexApiFacade bittrexService,
                              BinanceApiFacade binanceService,
                              CryptoCompareService cryptoCompareService,
                              AlphaVantageApiFacade alphaVantageService) {
        this.bittrexService = bittrexService;
        this.binanceService = binanceService;
        this.cryptoCompareService = cryptoCompareService;
        this.alphaVantageService = alphaVantageService;
    }

    public TickerDTO getTicker(Long exchangeId, ExchangeProduct exchangeProduct) {
        return getExchangeServiceByType(exchangeId).getTicker(exchangeProduct);
    }

    public List<? extends BalanceDTO> getBalances(Long exchangeId, String userId) {
        return getExchangeServiceByType(exchangeId).getBalances(userId);
    }

    public List<? extends OpenOrderDTO> getOpenOrders(ExchangeProduct exchangeProduct, String userId) {
        return getExchangeServiceByType(exchangeProduct.getExchangeId()).getOpenOrders(userId, exchangeProduct);
    }

    public List<? extends OrderHistoryDTO> getOrderHistory(ExchangeProduct exchangeProduct, String userId) {
        return getExchangeServiceByType(exchangeProduct.getExchangeId()).getOrderHistory(userId, exchangeProduct);
    }

    public List<? extends CandlestickDTO> getCandlesticks(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        return getExchangeServiceByType(exchangeProduct.getExchangeId()).getCandlesticks(exchangeProduct, periodEnum);
    }

    public CandlestickDTO getLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        return getExchangeServiceByType(exchangeProduct.getExchangeId()).getLatestCandlestick(exchangeProduct, periodEnum);
    }

    public List<? extends ExchangeProductDTO> getExchangeProductList(Long exchangeId) {
        return getExchangeServiceByType(exchangeId).getExchangeProductList();
    }

    public List<? extends MarketSummaryDTO> getMarketSummaries(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct) {
        return getExchangeServiceByType(exchangeEnum.getId()).getMarketSummaries(exchangeProduct);
    }

    public void refreshProductList() {
        cryptoCompareService.refreshProductList();
    }

    private ExchangeApiFacade getExchangeServiceByType(Long exchangeId) {
        ExchangeEnum exchangeEnum = ExchangeEnum.getById(exchangeId);
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
