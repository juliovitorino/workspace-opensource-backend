package br.com.jcv.brcities.corelayer.specs;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.commons.library.specs.AbstractCriteria;
import br.com.jcv.commons.library.specs.GenericSpecification;
import br.com.jcv.commons.library.specs.JoinTableMap;

@Service
public class CityUfSpecification  {

    private CityUfSpecification() {}

    public static Specification<CityUf> byUfId(String ufId) {
        if (ufId.isEmpty()) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ufId"), ufId));
    }

    public static Specification<CityUf> getAllCitiesFromUfUsingFilter(AbstractCriteria criteria) {
        Assert.notNull(criteria,"Filter must not be null");

        JoinTableMap<CityUf> joinTableMap = (root, query) -> {
            Map<String, Join<Object,Object>> joinMap = new LinkedHashMap<>();
            joinMap.put("city", root.join("city", JoinType.INNER));
            return joinMap;
        };

        return new GenericSpecification<>(criteria, joinTableMap);
    }

}
