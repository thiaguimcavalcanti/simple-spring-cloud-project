package com.bot.exchanges.cryptocompare.service.impl;

import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.cryptocompare.client.CryptoComparePublicClient;
import com.bot.exchanges.cryptocompare.service.CryptoCompareService;
import com.bot.exchanges.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoCompareServiceImpl implements CryptoCompareService {

    @Autowired
    private CryptoComparePublicClient cryptoComparePublicClient;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void refreshCoinList() {
        List<Product> products = cryptoComparePublicClient.getCoinList().getData();
        productRepository.saveAll(products);
    }
}
