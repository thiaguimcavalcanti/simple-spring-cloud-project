package com.bot.exchanges.commons.service.impl;

import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.ExchangeRepository;
import com.bot.exchanges.commons.service.ExchangeService;
import com.bot.exchanges.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ExchangeServiceImpl implements ExchangeService {

    protected ExchangeEnum exchangeEnum;

    @Autowired
    protected ExchangeProductRepository exchangeProductRepository;

    @Autowired
    protected ExchangeRepository exchangeRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Override
    public void refreshExchangeProductList() {
        Exchange exchange = exchangeRepository.findById((long) exchangeEnum.getId()).orElse(null);

        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId((long) exchangeEnum.getId());
        Map<String, ExchangeProduct> exchangeProductsByProduct = exchangeProducts.stream()
                .collect(Collectors.toMap(ep -> ep.getBaseProductId() + ep.getProductId(), ep -> ep));

        getExchangeProductList().forEach(bep -> {
            ExchangeProduct exchangeProduct = exchangeProductsByProduct.get(bep.getBaseProductId() + bep.getProductId());
            if (exchangeProduct != null) {
                exchangeProduct.setActive(bep.isActive());
            } else if (bep.isActive()) {
                exchangeProduct = new ExchangeProduct();
                exchangeProduct.setExchange(exchange);
                exchangeProduct.setProduct(getOrCreateProductReference(bep.getProductId()));
                exchangeProduct.setBaseProduct(getOrCreateProductReference(bep.getBaseProductId()));
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
