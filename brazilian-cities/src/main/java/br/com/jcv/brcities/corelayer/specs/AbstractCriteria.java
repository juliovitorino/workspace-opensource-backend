package br.com.jcv.brcities.corelayer.specs;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCriteria {

    public abstract Predicate toPredicate(Root root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Map<String, Join<Object, Object>> attributeToJoin);

    public Predicate getPredicate(Criteria criteria, CriteriaBuilder criteriaBuilder, Path expression) {

        Predicate predicate = null;
        switch (criteria.getOperator()) {
            case EQUAL_TO:
                predicate = criteriaBuilder.equal(expression, criteria.getValue());
                break;
            case LIKE:
                predicate = criteriaBuilder.like(expression, "%" + criteria.getValue() + "%");
                break;
            case STARTS_WITH:
                predicate = criteriaBuilder.like(expression, criteria.getValue() + "%");
                break;
            case IN:
                predicate = criteriaBuilder.in(expression).value(criteria.getValue());
                break;
            case GT:
                predicate = criteriaBuilder.greaterThan(expression, (Comparable) criteria.getValue());
                break;
            case LT:
                predicate = criteriaBuilder.lessThan(expression, (Comparable) criteria.getValue());
                break;
            case GTE:
                predicate = criteriaBuilder.greaterThanOrEqualTo(expression, (Comparable) criteria.getValue());
                break;
            case LTE:
                predicate = criteriaBuilder.lessThanOrEqualTo(expression, (Comparable) criteria.getValue());
                break;
            case NOT_EQUAL:
                predicate = criteriaBuilder.notEqual(expression, criteria.getValue());
                break;
            case IS_NULL:
                predicate = criteriaBuilder.isNull(expression);
                break;
            case NOT_NULL:
                predicate = criteriaBuilder.isNotNull(expression);
                break;
            default:
                log.error("Invalid Operator");
                throw new IllegalArgumentException(criteria.getOperator() + " is not valid operator");
        }
        return predicate;
    }

    public Predicate getPredicateFromFilter(Criteria criteria, Root root, CriteriaBuilder criteriaBuilder, Map<String, Join<Object, Object>> attributeToJoin) {
        Assert.notNull(criteria,"Filter must not be null");
        if (attributeToJoin != null && attributeToJoin.get(criteria.getEntityName()) != null) {
            return  getPredicate(criteria, criteriaBuilder, attributeToJoin.get(criteria.getEntityName()).get(criteria.getField()));
        } else {
            return getPredicate(criteria, criteriaBuilder, root.get(criteria.getField()));
        }
    }
}