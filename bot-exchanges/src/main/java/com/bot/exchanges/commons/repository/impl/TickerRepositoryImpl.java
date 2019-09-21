package com.bot.exchanges.commons.repository.impl;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.ExchangeProduct_;
import com.bot.exchanges.commons.entities.Ticker;
import com.bot.exchanges.commons.entities.Ticker_;
import com.bot.exchanges.commons.repository.CustomTickerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TickerRepositoryImpl implements CustomTickerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ticker> findByExchangeId(Long exchangeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticker> cq = cb.createQuery(Ticker.class);
        Root<Ticker> root = cq.from(Ticker.class);

        Join<Ticker, ExchangeProduct> joinExchangeProduct = root.join(Ticker_.exchangeProduct);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(joinExchangeProduct.get(ExchangeProduct_.exchangeId), exchangeId));

        cq.where(predicates.toArray(new Predicate[0]));

        Query query = entityManager.createQuery(cq);

        return query.getResultList();
    }
}
