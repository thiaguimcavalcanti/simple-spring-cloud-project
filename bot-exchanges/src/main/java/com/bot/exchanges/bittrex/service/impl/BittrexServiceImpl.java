package com.bot.exchanges.bittrex.service.impl;

import com.bot.exchanges.binance.dto.publicapi.BinanceCandlestickDTO;
import com.bot.exchanges.bittrex.client.BittrexAccountClient;
import com.bot.exchanges.bittrex.client.BittrexMarketClient;
import com.bot.exchanges.bittrex.client.BittrexPublicClient;
import com.bot.exchanges.bittrex.dto.account.BittrexBalanceDTO;
import com.bot.exchanges.bittrex.dto.account.BittrexOrderHistoryDTO;
import com.bot.exchanges.bittrex.dto.market.BittrexOpenOrderDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexProductDTO;
import com.bot.exchanges.bittrex.dto.publicapi.BittrexTickerDTO;
import com.bot.exchanges.bittrex.service.BittrexService;
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
public class BittrexServiceImpl implements BittrexService {

    @Autowired
    private BittrexAccountClient bittrexAccountClient;

    @Autowired
    private BittrexPublicClient bittrexPublicClient;

    @Autowired
    private BittrexMarketClient bittrexMarketClient;

    @Autowired
    private ExchangeProductRepository exchangeProductRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public BittrexTickerDTO getTicker(String market) {
        return bittrexPublicClient.ticker(market).getResult();
    }

    @Override
    public List<BittrexBalanceDTO> getBalances(String userId) {
        return bittrexAccountClient.getBalances(userId).getResult();
    }

    @Override
    public List<BittrexOpenOrderDTO> getOpenOrders(String userId, String market) {
        return bittrexMarketClient.getOpenOrders(userId, market).getResult();
    }

    @Override
    public List<BittrexOrderHistoryDTO> getOrderHistory(String userId, String market) {
        return bittrexAccountClient.getOrderHistory(userId, market).getResult();
    }

    @Override
    public List<? extends BinanceCandlestickDTO> getCandlesticks(String market, String interval) {
        return null;
    }

    @Override
    public void refreshExchangeProductList() {
        List<BittrexProductDTO> markets = bittrexPublicClient.getMarkets().getResult();

        long exchangeId = ExchangeEnum.BITTREX.getId();
        Exchange exchange = exchangeRepository.findById(exchangeId).orElse(null);

        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeId);
        Map<String, ExchangeProduct> exchangeProductsByProduct = exchangeProducts.stream()
                .collect(Collectors.toMap(ep -> ep.getBaseProductId() + ep.getProductId(), ep -> ep));

        markets.forEach(bep -> {
            ExchangeProduct exchangeProduct = exchangeProductsByProduct.get(bep.getBaseCurrency() + bep.getMarketCurrency());
            if (exchangeProduct != null) {
                exchangeProduct.setActive(bep.isActive());
            } else if (bep.isActive()) {
                exchangeProduct = new ExchangeProduct();
                exchangeProduct.setExchange(exchange);
                exchangeProduct.setProduct(getOrCreateProductReference(bep.getMarketCurrency()));
                exchangeProduct.setBaseProduct(getOrCreateProductReference(bep.getBaseCurrency()));
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
