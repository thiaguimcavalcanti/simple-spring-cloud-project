package com.bot.exchanges.commons.service.impl;

import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.ExchangeRepository;
import com.bot.exchanges.commons.service.ExchangeService;
import com.bot.exchanges.repository.ProductRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        Set<String> productsToSearch = new HashSet<>();
        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId((long) exchangeEnum.getId());
        Map<String, ExchangeProduct> exchangeProductsByProduct = exchangeProducts.stream()
                .collect(Collectors.toMap(ep -> {
                    productsToSearch.add(ep.getBaseProductId());
                    productsToSearch.add(ep.getProductId());
                    return ep.getBaseProductId() + ep.getProductId();
                }, ep -> ep));

        Map<String, Product> products = getProducts(productsToSearch);

        getExchangeProductList().forEach(bep -> {
            ExchangeProduct exchangeProduct = exchangeProductsByProduct.get(bep.getBaseProductId() + bep.getProductId());
            if (exchangeProduct != null) {
                exchangeProduct.setActive(bep.isActive());
            } else if (bep.isActive()) {
                exchangeProduct = new ExchangeProduct();
                exchangeProduct.setExchange(exchange);
                exchangeProduct.setProduct(getOrCreateProductReference(products, bep.getProductId()));
                exchangeProduct.setBaseProduct(getOrCreateProductReference(products, bep.getBaseProductId()));
                exchangeProduct.setActive(bep.isActive());
                exchangeProducts.add(exchangeProduct);
            }
        });

        exchangeProductRepository.saveAll(exchangeProducts);
    }

    private Map<String, Product> getProducts(Set<String> productsToSearch) {
        List<Product> products = null;
        if (CollectionUtils.isNotEmpty(productsToSearch)) {
            products = productRepository.findAllById(productsToSearch);
        } else {
            products = productRepository.findAll();
        }
        return products.stream().collect(Collectors.toMap(Product::getId, p -> p));
    }

    private Product getOrCreateProductReference(Map<String, Product> products, String symbol) {
        Product product = products.get(symbol);
        if (product == null) {
            product = new Product(symbol);
            product = productRepository.save(product);
            products.put(symbol, product);
        }
        return product;
    }
}
