package com.bot.exchanges.cryptocompare.service.impl;

import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.commons.repository.ProductRepository;
import com.bot.exchanges.cryptocompare.client.CryptoComparePublicClient;
import com.bot.exchanges.cryptocompare.service.CryptoCompareService;
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
}
