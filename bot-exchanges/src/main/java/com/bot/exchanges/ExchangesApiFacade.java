package com.bot.exchanges;

import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.bittrex.service.BittrexService;
import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.service.ExchangeService;
import com.bot.exchanges.cryptocompare.service.CryptoCompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangesApiFacade {

    @Autowired
    private BittrexService bittrexService;

    @Autowired
    private BinanceService binanceService;

    @Autowired
    private CryptoCompareService cryptoCompareService;

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

    public List<? extends CandlestickDTO> getCandlesticks(ExchangeEnum exchangeEnum, String market, String interval) {
        return getExchangeServiceByType(exchangeEnum).getCandlesticks(market, interval);
    }

    public void refreshProductList() {
        cryptoCompareService.refreshCoinList();
        binanceService.refreshProductList();
        bittrexService.refreshProductList();
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
