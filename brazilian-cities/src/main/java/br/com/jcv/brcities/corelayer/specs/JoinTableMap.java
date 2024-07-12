package br.com.jcv.brcities.corelayer.specs;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public interface JoinTableMap<T> {
    Map<String, Join<Object,Object>> getJoinTableMap(Root<T> root, CriteriaQuery<?> query);
}
