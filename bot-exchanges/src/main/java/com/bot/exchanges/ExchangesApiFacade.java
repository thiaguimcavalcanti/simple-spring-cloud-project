package com.bot.exchanges;

import com.bot.commons.dto.BalanceDTO;
import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.dto.OrderDTO;
import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.alphavantage.AlphaVantageApiFacade;
import com.bot.exchanges.binance.BinanceApiFacade;
import com.bot.exchanges.bittrex.BittrexApiFacade;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.ExchangeApiFacade;
import com.bot.exchanges.commons.entities.OrderHistory;
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

    public TickerDTO getTicker(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct) {
        return getExchangeServiceByType(exchangeEnum.getId()).getTicker(exchangeProduct);
    }

    public List<? extends BalanceDTO> getBalances(Long exchangeId, String userId) {
        return getExchangeServiceByType(exchangeId).getBalances(userId);
    }

    public List<? extends OrderDTO> getOpenOrders(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct, String userId) {
        return getExchangeServiceByType(exchangeEnum.getId()).getOpenOrders(userId, exchangeProduct);
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

    public OrderDTO createNewOrder(OrderHistory orderHistory) {
        return getExchangeServiceByType(orderHistory.getExchangeProduct().getExchangeId()).createNewOrder(orderHistory);
    }

    public OrderDTO cancelOrder(String userId, ExchangeProduct exchangeProduct, String orderId) {
        return getExchangeServiceByType(exchangeProduct.getExchangeId()).cancelOrder(userId, exchangeProduct, orderId);
    }

    public List<? extends OrderDTO> getAllOrders(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct, String userId) {
        return getExchangeServiceByType(exchangeEnum.getId()).getAllOrders(userId, exchangeProduct);
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
