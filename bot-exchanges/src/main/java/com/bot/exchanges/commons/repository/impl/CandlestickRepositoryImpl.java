package com.bot.exchanges.commons.repository.impl;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.Candlestick_;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.ExchangeProduct_;
import com.bot.exchanges.commons.repository.CustomCandlestickRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CandlestickRepositoryImpl implements CustomCandlestickRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Candlestick> getToAnalyze(ExchangeProduct exchangeProduct, ZonedDateTime beginTime,
                                          ZonedDateTime endTime, PeriodEnum periodEnum) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Candlestick> cq = cb.createQuery(Candlestick.class);
        Root<Candlestick> root = cq.from(Candlestick.class);

        Join<Candlestick, ExchangeProduct> joinExchangeProduct = root.join(Candlestick_.exchangeProduct);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(Candlestick_.periodEnum), periodEnum));
        predicates.add(cb.equal(root.join(Candlestick_.exchangeProduct), exchangeProduct));
        predicates.add(cb.greaterThanOrEqualTo(root.get(Candlestick_.beginTime), beginTime));

        if (endTime != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Candlestick_.endTime), endTime));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        cq.orderBy(cb.asc(joinExchangeProduct.get(ExchangeProduct_.exchangeId)),
                cb.asc(root.get(Candlestick_.beginTime)));

        Query query = entityManager.createQuery(cq);

        List<Candlestick> resultList = query.getResultList();

        entityManager.clear();

        return resultList;
    }

}
