package com.bot.exchanges.commons.service.impl;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.utils.DateUtils;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.MarketSummary;
import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.ExchangeRepository;
import com.bot.exchanges.commons.repository.MarketSummaryRepository;
import com.bot.exchanges.commons.repository.ProductRepository;
import com.bot.exchanges.commons.service.ExchangeService;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ExchangeServiceImpl implements ExchangeService {

    private static final Logger LOG = LogManager.getLogger(ExchangeServiceImpl.class.getName());

    protected ExchangeEnum exchangeEnum;

    @Autowired
    protected ExchangeProductRepository exchangeProductRepository;

    @Autowired
    protected ExchangeRepository exchangeRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected MarketSummaryRepository marketSummaryRepository;

    @Autowired
    protected CandlestickRepository candlestickRepository;

    @Autowired
    private StrategyAnalysisService strategyAnalysisService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void refreshLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        LOG.warn("refreshLatestCandlestick - " + exchangeEnum + " - " + periodEnum + ": " + exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId());

        // Latest candlestick
        CandlestickDTO latestCandlestickDTO = getLatestCandlestick(exchangeProduct, periodEnum);
        Candlestick latestCandlestick = mapper.map(latestCandlestickDTO, Candlestick.class);
        fillAdditionalCandlestickInfo(exchangeProduct, periodEnum, latestCandlestick);

        // Previous candlestick to compare with the latest
        Candlestick previousCandlestick = candlestickRepository
                .findTopByExchangeProductIdAndPeriodEnumOrderByBeginTimeDesc(exchangeProduct.getId(), periodEnum);

        // Refresh all the candlesticks from this Exchange Product if the latestCandlestick is not the next expected tick
        if (latestCandlestick == null || previousCandlestick == null ||
                previousCandlestick.getEndTime().compareTo(latestCandlestick.getBeginTime()) < 0) {
            refreshCandlestick(exchangeProduct, periodEnum);
        }

        if (latestCandlestick != null) {
            candlestickRepository.save(latestCandlestick);

            // Perform the technical analysis (async task)
            if (previousCandlestick == null || previousCandlestick.getBeginTime().compareTo(latestCandlestick.getBeginTime()) < 0) {
                strategyAnalysisService.analyzeStrategies(exchangeProduct, latestCandlestick.getEndTime(), periodEnum);
            }
        }
    }

    @Override
    @Async
    public List<? extends CandlestickDTO> refreshCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        LOG.warn("refreshCandlestick - "+ exchangeEnum + " - " + periodEnum + ": " + exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId());

        List<? extends CandlestickDTO> candlesticksDTO = getCandlesticks(exchangeProduct, periodEnum);

        if (CollectionUtils.isNotEmpty(candlesticksDTO)) {
            // Ignore the last one, because this candlestick is still opened
            candlesticksDTO.remove(candlesticksDTO.size() - 1);

            List<Candlestick> candlesticks = candlesticksDTO.stream().map(candlestickDTO -> {
                Candlestick candlestick = mapper.map(candlestickDTO, Candlestick.class);
                fillAdditionalCandlestickInfo(exchangeProduct, periodEnum, candlestick);
                return candlestick;
            }).collect(Collectors.toList());

            candlestickRepository.saveAll(candlesticks);
            candlestickRepository.flush();
        }

        return candlesticksDTO;
    }

    @Override
    public void refreshExchangeProductList() {
        LOG.warn("refreshExchangeProductList - "+ exchangeEnum);

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

    @Override
    public void refreshMarketSummaries(ExchangeProduct exchangeProduct) {
        List<? extends MarketSummaryDTO> marketSummariesDTO = getMarketSummaries(exchangeProduct);

        if (CollectionUtils.isNotEmpty(marketSummariesDTO)) {
            ZonedDateTime latestTickDate = DateUtils.roundZonedDateTime(ZonedDateTime.now(), Duration.ofMinutes(5));

            Map<String, ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeEnum.getId())
                    .stream() .collect(Collectors.toMap(ep -> ep.getBaseProductId() + ep.getProductId(), ep -> ep));

            List<MarketSummary> marketSummaries = new ArrayList<>();
            marketSummariesDTO.forEach(marketSummaryDTO -> {
                ExchangeProduct ep = exchangeProducts.get(marketSummaryDTO.getBaseProduct() + marketSummaryDTO.getProduct());
                if (ep != null) {
                    MarketSummary marketSummary = mapper.map(marketSummaryDTO, MarketSummary.class);
                    marketSummary.setExchangeProduct(ep);
                    marketSummary.setTickDate(latestTickDate);
                    marketSummaries.add(marketSummary);
                }
            });

            marketSummaryRepository.saveAll(marketSummaries);
            marketSummaryRepository.flush();
        }
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

    private void fillAdditionalCandlestickInfo(ExchangeProduct exchangeProduct, PeriodEnum periodEnum,
                                               Candlestick latestCandlestick) {
        if (latestCandlestick != null) {
            latestCandlestick.setExchangeProduct(exchangeProduct);
            latestCandlestick.setPeriodEnum(periodEnum);

            StringBuilder sb = new StringBuilder();
            latestCandlestick.setId(sb.append(latestCandlestick.getExchangeProduct().getId()).append("-")
                    .append(latestCandlestick.getPeriodEnum().getDuration().toString()).append("-")
                    .append(latestCandlestick.getBeginTime().toEpochSecond()).append("-")
                    .append(latestCandlestick.getEndTime().toEpochSecond()).toString().toUpperCase());
        }
    }
}
