package com.bot.exchanges.commons.repository.impl;

import com.bot.commons.enums.OrderStatusEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.ExchangeProduct_;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.OrderHistory_;
import com.bot.exchanges.commons.repository.CustomOrderHistoryRepository;

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

public class OrderHistoryRepositoryImpl implements CustomOrderHistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderHistory> findByExchangeIdAndStatusIn(Long exchangeId, List<OrderStatusEnum> orderStatusEnums) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderHistory> cq = cb.createQuery(OrderHistory.class);
        Root<OrderHistory> root = cq.from(OrderHistory.class);

        Join<OrderHistory, ExchangeProduct> joinExchangeProduct = root.join(OrderHistory_.exchangeProduct);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(joinExchangeProduct.get(ExchangeProduct_.exchangeId), exchangeId));
        predicates.add(root.get(OrderHistory_.status).in(orderStatusEnums));

        cq.where(predicates.toArray(new Predicate[0]));

        Query query = entityManager.createQuery(cq);

        return query.getResultList();
    }
}
