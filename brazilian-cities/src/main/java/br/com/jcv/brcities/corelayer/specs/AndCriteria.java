package br.com.jcv.brcities.corelayer.specs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AndCriteria extends AbstractCriteria {

    private final List<AbstractCriteria> criterias;

    public AndCriteria(List<AbstractCriteria> criterias) {
        this.criterias = criterias;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Map<String, Join<Object, Object>> attributeToJoin ) {
        return criteriaBuilder.and(
                criterias.stream().map(filter -> filter.toPredicate(root,query,criteriaBuilder,attributeToJoin))
                        .collect(Collectors.toList())
                        .toArray(Predicate[]::new));
    }
}