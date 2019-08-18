package com.bot.exchanges.binance.service.impl;

import com.bot.exchanges.binance.client.BinancePublicClient;
import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.binance.dto.publicapi.BinanceProductDTO;
import com.bot.exchanges.binance.service.BinanceService;
import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.ExchangeRepository;
import com.bot.exchanges.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BinanceServiceImpl implements BinanceService {

    public static final String INVALID_SYMBOL = "123";
    @Autowired
    private BinancePublicClient binancePublicClient;

    @Autowired
    private ExchangeProductRepository exchangeProductRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public TickerDTO getTicker(String market) {
        return null;
    }

    @Override
    public List<? extends BalanceDTO> getBalances(String userId) {
        return null;
    }

    @Override
    public List<? extends OpenOrderDTO> getOpenOrders(String userId, String market) {
        return null;
    }

    @Override
    public List<? extends OrderHistoryDTO> getOrderHistory(String userId, String market) {
        return null;
    }

    @Override
    public List<? extends BinanceCandlestickDTO> getCandlesticks(String symbol, String interval) {
        return binancePublicClient.getCandlesticks(symbol, interval, null, null, null);
    }

    @Override
    public void refreshExchangeProductList() {
        List<BinanceProductDTO> symbols = binancePublicClient.getExchangeInfo().getSymbols();

        long exchangeId = ExchangeEnum.BINANCE.getId();
        Exchange exchange = exchangeRepository.findById(exchangeId).orElse(null);

        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeId);
        Map<String, ExchangeProduct> exchangeProductsByProduct = exchangeProducts.stream()
                .collect(Collectors.toMap(ep -> ep.getBaseProductId() + ep.getProductId(), ep -> ep));

        symbols.forEach(bep -> {
            ExchangeProduct exchangeProduct = exchangeProductsByProduct.get(bep.getBaseAsset() + bep.getQuoteAsset());
            if (exchangeProduct != null) {
                exchangeProduct.setActive(bep.isActive());
            } else if (bep.isActive()) {
                exchangeProduct = new ExchangeProduct();
                exchangeProduct.setExchange(exchange);
                exchangeProduct.setProduct(getOrCreateProductReference(bep.getQuoteAsset()));
                exchangeProduct.setBaseProduct(getOrCreateProductReference(bep.getBaseAsset()));
                exchangeProduct.setActive(bep.isActive());
                exchangeProducts.add(exchangeProduct);
            }
        });

        exchangeProductRepository.saveAll(exchangeProducts);
    }

    private Product getOrCreateProductReference(String symbol) {
        if (!productRepository.existsById(symbol)) {
            Product product = new Product(symbol);
            return productRepository.save(product);
        }
        return productRepository.getOne(symbol);
     }
}
