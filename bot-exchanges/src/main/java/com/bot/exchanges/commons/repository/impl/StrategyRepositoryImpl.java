package com.bot.exchanges.commons.repository.impl;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.ExchangeProduct_;
import com.bot.exchanges.commons.entities.Strategy;
import com.bot.exchanges.commons.entities.Strategy_;
import com.bot.exchanges.commons.repository.CustomStrategyRepository;

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

public class StrategyRepositoryImpl implements CustomStrategyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Strategy> findByActiveIsTrueAndExchangeId(Long exchangeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Strategy> cq = cb.createQuery(Strategy.class);
        Root<Strategy> root = cq.from(Strategy.class);

        Join<Strategy, ExchangeProduct> joinExchangeProduct = root.join(Strategy_.exchangeProduct);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(joinExchangeProduct.get(ExchangeProduct_.exchangeId), exchangeId));

        cq.where(predicates.toArray(new Predicate[0]));

        Query query = entityManager.createQuery(cq);

        return query.getResultList();
    }
}
