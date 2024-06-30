package br.com.jcv.brcities.corelayer.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

   private final AbstractCriteria criteriaConcrete;
   private final JoinDataSupplier<T> joinDataSupplier;

    public GenericSpecification(AbstractCriteria criteriaConcrete, JoinDataSupplier<T> joinDataSupplier) {
        this.criteriaConcrete = criteriaConcrete;
        this.joinDataSupplier = joinDataSupplier;
    }

    @Override
   public Predicate toPredicate(
           Root<T> root,
           CriteriaQuery<?> query,
           CriteriaBuilder criteriaBuilder) {

      if (joinDataSupplier != null && criteriaConcrete !=null) {
         return criteriaConcrete.toPredicate(root, query, criteriaBuilder, joinDataSupplier.getJoinData(root,query));
      }
      return criteriaBuilder.conjunction();
   }
}