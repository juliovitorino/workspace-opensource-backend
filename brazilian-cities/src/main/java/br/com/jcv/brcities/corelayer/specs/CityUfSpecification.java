package br.com.jcv.brcities.corelayer.specs;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.jcv.brcities.corelayer.model.CityUf;

public interface CityUfSpecification extends JpaRepository<CityUf, Long>, JpaSpecificationExecutor<CityUf> {

    static Specification<CityUf> byUfId(String ufId) {
        if (ufId.isEmpty()) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ufId"), ufId));
    }

    static Specification<CityUf> getAllCitiesFromUfUsingFilter(AbstractCriteria criteria) {
        if (Objects.isNull(criteria)) return null;

        JoinDataSupplier<CityUf> joinDataSupplier = (root, query) -> {
            Map<String, Join<Object,Object>> joinMap = new LinkedHashMap<>();

            Join<Object,Object> joinCity = root.join("city", JoinType.INNER);
            joinMap.put("city", joinCity);

            //query.multiselect(root.get("cityId"), joinCity.get("name")); --> error... it doesn't work, yet! :(

            return joinMap;
        };

        return new GenericSpecification<>(criteria, joinDataSupplier);
    }

}
