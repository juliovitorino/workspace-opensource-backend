package br.com.jcv.brcities.corelayer.specs;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SimpleCriteria<T> extends AbstractCriteria {

    private final Criteria criteria;

    public SimpleCriteria(Criteria<T> criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Map<String, Join<Object, Object>> attributeToJoin) {
        return getPredicateFromFilter(criteria,root,criteriaBuilder,attributeToJoin);
    }
}