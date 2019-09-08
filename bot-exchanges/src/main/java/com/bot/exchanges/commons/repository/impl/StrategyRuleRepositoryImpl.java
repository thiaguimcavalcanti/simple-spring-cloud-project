package com.bot.exchanges.commons.repository.impl;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.Strategy;
import com.bot.exchanges.commons.entities.StrategyRule;
import com.bot.exchanges.commons.entities.StrategyRule_;
import com.bot.exchanges.commons.entities.Strategy_;
import com.bot.exchanges.commons.repository.CustomStrategyRuleRepository;

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

public class StrategyRuleRepositoryImpl implements CustomStrategyRuleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StrategyRule> findByExchangeProductAndPeriodEnum(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StrategyRule> cq = cb.createQuery(StrategyRule.class);
        Root<StrategyRule> root = cq.from(StrategyRule.class);

        Join<StrategyRule, Strategy> joinStrategy = root.join(StrategyRule_.strategy);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(joinStrategy.get(Strategy_.exchangeProduct), exchangeProduct));
        predicates.add(cb.isTrue(joinStrategy.get(Strategy_.active)));
        predicates.add(cb.equal(root.get(StrategyRule_.periodEnum), periodEnum));

        Predicate buySellActive = cb.or(cb.isTrue(root.get(StrategyRule_.buyActive)),
                cb.isTrue(root.get(StrategyRule_.sellActive)));
        predicates.add(buySellActive);

        cq.where(predicates.toArray(new Predicate[0]));

        Query query = entityManager.createQuery(cq);

        return query.getResultList();
    }
}
