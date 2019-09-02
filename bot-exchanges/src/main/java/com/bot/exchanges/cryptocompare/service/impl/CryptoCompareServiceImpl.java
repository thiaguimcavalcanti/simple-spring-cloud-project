package com.bot.exchanges.cryptocompare.service.impl;

import static com.bot.commons.utils.DateUtils.roundZonedDateTime;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.repository.ProductRepository;
import com.bot.exchanges.cryptocompare.client.CryptoComparePublicClient;
import com.bot.exchanges.cryptocompare.dto.CryptoCompareCandlestickDTO;
import com.bot.exchanges.cryptocompare.service.CryptoCompareService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoCompareServiceImpl implements CryptoCompareService {

    private final CryptoComparePublicClient cryptoComparePublicClient;

    private final ProductRepository productRepository;

    @Autowired
    public CryptoCompareServiceImpl(CryptoComparePublicClient cryptoComparePublicClient,
                                    ProductRepository productRepository) {
        this.cryptoComparePublicClient = cryptoComparePublicClient;
        this.productRepository = productRepository;
    }

    @Override
    public void refreshProductList() {
        List<Product> products = cryptoComparePublicClient.getCoinList().getData();
        productRepository.saveAll(products);
        productRepository.flush();
    }

    @Override
    public CandlestickDTO getLatestCandlestick(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        List<CryptoCompareCandlestickDTO> candlesticks = cryptoComparePublicClient.historicalMinute(exchangeProduct.getProductId(),
                exchangeProduct.getBaseProductId(), 2, exchangeEnum.getCryptoCompareName(), periodEnum.getInMinutes(), false).getData();

        if (CollectionUtils.isNotEmpty(candlesticks) && candlesticks.size() >= 2) {
            CryptoCompareCandlestickDTO candlestickDTO = candlesticks.get(candlesticks.size() - 2);

            if (candlestickDTO != null) {
                if (ExchangeEnum.BITTREX.equals(exchangeEnum)) {
                    candlestickDTO.setBeginTime(candlestickDTO.getEndTime());
                    candlestickDTO.setEndTime(candlestickDTO.getEndTime().plus(periodEnum.getDuration()));
                } else if (ExchangeEnum.BINANCE.equals(exchangeEnum)) {
                    candlestickDTO.setBeginTime(roundZonedDateTime(candlestickDTO.getBeginTime(), periodEnum.getDuration()));
                    candlestickDTO.setEndTime(roundZonedDateTime(candlestickDTO.getEndTime(), periodEnum.getDuration()));
                }
            }

            return candlestickDTO;
        }

        return null;
    }
}
