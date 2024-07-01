package br.com.jcv.brcities.corelayer.specs;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.Assert;

import br.com.jcv.brcities.corelayer.model.CityUf;

public interface CityUfSpecification extends JpaRepository<CityUf, Long>, JpaSpecificationExecutor<CityUf> {

    static Specification<CityUf> byUfId(String ufId) {
        if (ufId.isEmpty()) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ufId"), ufId));
    }

    static Specification<CityUf> getAllCitiesFromUfUsingFilter(AbstractCriteria criteria) {
        Assert.notNull(criteria,"Filter must not be null");

        JoinTableMap<CityUf> joinTableMap = (root, query) -> {
            Map<String, Join<Object,Object>> joinMap = new LinkedHashMap<>();

            Join<Object,Object> joinCity = root.join("city", JoinType.INNER);
            joinMap.put("city", joinCity);

            Path<Long> cityIdPath = root.get("cityId");
            Path<String> nameCityPath = joinCity.get("name");

//            query.select(root.get("cityId")); // fail:: try 25
            query.multiselect(cityIdPath, nameCityPath); // fail:: try 38 :: The query fields are being modified but isn't work :(

            return joinMap;
        };

        return new GenericSpecification<>(criteria, joinTableMap);
    }

}
