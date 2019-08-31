package com.bot.exchanges.commons.service.impl;

import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.entities.types.CustomBigDecimal;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.ExchangeRepository;
import com.bot.exchanges.commons.repository.ProductRepository;
import com.bot.exchanges.commons.service.ExchangeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    @Autowired
    protected CandlestickRepository candlestickRepository;

    @Override
    public Candlestick refreshLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        // Latest candlestick
        CandlestickDTO latestCandlestickDTO = getLatestCandlestick(exchangeProduct, periodEnum);
        Candlestick latestCandlestick = convertCandlestickDTOToEntity(exchangeProduct, periodEnum, latestCandlestickDTO);

        // Previous candlestick
        Candlestick previousCandlestick = candlestickRepository
                .findTopByExchangeProductIdAndPeriodEnumOrderByBeginTimeDesc(exchangeProduct.getId(), periodEnum);
        ZonedDateTime previousEndTime = getPreviousEndTime(periodEnum, previousCandlestick);

        if (latestCandlestick == null || previousEndTime.compareTo(latestCandlestick.getBeginTime()) < 0) {
            refreshCandlestick(exchangeProduct, periodEnum);
        }

        return latestCandlestick != null ? candlestickRepository.save(latestCandlestick) : null;
    }

    private ZonedDateTime getPreviousEndTime(PeriodEnum periodEnum, Candlestick previousCandlestick) {
        if (previousCandlestick == null) {
            ZonedDateTime previousEndTime = ZonedDateTime.now().withSecond(0).withNano(0);
            return previousEndTime.minusMinutes(previousEndTime.getMinute() % periodEnum.getInMinutes())
                    .minusMinutes(periodEnum.getInMinutes());
        }
        return previousCandlestick.getEndTime();
    }

    @Override
    public List<Candlestick> refreshCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        List<? extends CandlestickDTO> candlesticksDTO = getCandlesticks(exchangeProduct, periodEnum);
        List<Candlestick> candlesticks = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(candlesticksDTO)) {
            candlesticks = candlesticksDTO.stream().map(candlestickDTO ->
                    convertCandlestickDTOToEntity(exchangeProduct, periodEnum, candlestickDTO))
                    .collect(Collectors.toList());

            // Ignore the last one, because this candlestick is still opened
            candlesticks.remove(candlesticks.size() - 1);

            candlesticks = candlestickRepository.saveAll(candlesticks);
            candlestickRepository.flush();
        }

        return candlesticks;
    }

    @Override
    public void refreshExchangeProductList() {
        Exchange exchange = exchangeRepository.findById(exchangeEnum.getId()).orElse(null);

        Set<String> productsToSearch = new HashSet<>();
        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeEnum.getId());
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

    private Candlestick convertCandlestickDTOToEntity(ExchangeProduct exchangeProduct, PeriodEnum periodEnum, CandlestickDTO candlestickDTO) {
        if (candlestickDTO == null) { return null; }

        Candlestick candlestick = new Candlestick();
        candlestick.setOpenPrice(CustomBigDecimal.valueOf(candlestickDTO.getOpen()));
        candlestick.setClosePrice(CustomBigDecimal.valueOf(candlestickDTO.getClose()));
        candlestick.setVolume(CustomBigDecimal.valueOf(candlestickDTO.getVolume()));
        candlestick.setMaxPrice(CustomBigDecimal.valueOf(candlestickDTO.getHigh()));
        candlestick.setMinPrice(CustomBigDecimal.valueOf(candlestickDTO.getLow()));
        candlestick.setEndTime(exchangeEnum.getParseDateFunction().apply(candlestickDTO.getCloseTime(), periodEnum.getDuration()));
        candlestick.setBeginTime(candlestick.getEndTime().minus(periodEnum.getDuration()));
        candlestick.setExchangeProduct(exchangeProduct);
        candlestick.setPeriodEnum(periodEnum);
        candlestick.setId(candlestick.toString());
        return candlestick;
    }
}
