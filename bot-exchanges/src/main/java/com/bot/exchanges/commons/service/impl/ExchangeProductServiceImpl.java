package com.bot.exchanges.commons.service.impl;

import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.utils.DateUtils;
import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.MarketSummary;
import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.ExchangeRepository;
import com.bot.exchanges.commons.repository.MarketSummaryRepository;
import com.bot.exchanges.commons.repository.ProductRepository;
import com.bot.exchanges.commons.service.ExchangeProductService;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExchangeProductServiceImpl implements ExchangeProductService {

    private static final Logger LOG = LogManager.getLogger(ExchangeProductServiceImpl.class.getName());

    private ExchangeProductRepository exchangeProductRepository;
    private ExchangeRepository exchangeRepository;
    private ProductRepository productRepository;
    private ExchangesApiFacade exchangesApiFacade;
    private ModelMapper mapper;

    @Autowired
    public ExchangeProductServiceImpl(ExchangeProductRepository exchangeProductRepository,
                                      ExchangeRepository exchangeRepository,
                                      ProductRepository productRepository,
                                      ExchangesApiFacade exchangesApiFacade,
                                      ModelMapper mapper) {
        this.exchangeProductRepository = exchangeProductRepository;
        this.exchangeRepository = exchangeRepository;
        this.productRepository = productRepository;
        this.exchangesApiFacade = exchangesApiFacade;
        this.mapper = mapper;
    }

    @Override
    public void refreshAll(ExchangeEnum exchangeEnum) {
        Exchange exchange = exchangeRepository.findById(exchangeEnum.getId()).orElse(null);

        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeEnum.getId());
        Set<String> productsToSearch = new HashSet<>();
        Map<String, ExchangeProduct> exchangeProductsByProduct = exchangeProducts.stream()
                .collect(Collectors.toMap(ep -> {
                    productsToSearch.add(ep.getBaseProductId());
                    productsToSearch.add(ep.getProductId());
                    return ep.getBaseProductId() + ep.getProductId();
                }, ep -> ep));

        Map<String, Product> products = getProducts(productsToSearch);

        exchangesApiFacade.getExchangeProductList(exchangeEnum.getId()).forEach(bep -> {
            ExchangeProduct exchangeProduct = exchangeProductsByProduct.get(bep.getBaseProductId() + bep.getProductId());
            if (exchangeProduct != null) {
                exchangeProduct.setActive(bep.isActive());
            } else {
                exchangeProduct = new ExchangeProduct();
                exchangeProduct.setExchange(exchange);
                exchangeProduct.setProduct(getOrCreateProductReference(products, bep.getProductId()));
                exchangeProduct.setBaseProduct(getOrCreateProductReference(products, bep.getBaseProductId()));
                exchangeProduct.setActive(bep.isActive());
                exchangeProducts.add(exchangeProduct);
            }
            exchangeProduct.setTickSize(bep.getTickSize());
            exchangeProduct.setMinPrice(bep.getMinPrice());
            exchangeProduct.setMaxPrice(bep.getMaxPrice());
            exchangeProduct.setMinQty(bep.getMinQty());
            exchangeProduct.setMaxQty(bep.getMaxQty());
            exchangeProduct.setQtySize(bep.getQtySize());
            exchangeProduct.setMinTotal(bep.getMinTotal());
        });

        exchangeProductRepository.saveAll(exchangeProducts);
    }

    @Override
    public ExchangeProduct getExchangeProduct(ExchangeEnum exchangeEnum, String baseProductId, String productId) {
        baseProductId = StringUtils.isBlank(baseProductId) ? null : baseProductId;
        productId = StringUtils.isBlank(productId) ? null : productId;
        return exchangeProductRepository.findByExchangeIdAndBaseProductIdAndProductId(exchangeEnum.getId(),
                baseProductId, productId);
    }

    @Override
    public List<ExchangeProduct> findByExchangeId(Long id) {
        return exchangeProductRepository.findByExchangeId(id);
    }

    private Map<String, Product> getProducts(Set<String> productsToSearch) {
        List<Product> products;
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
