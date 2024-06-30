package br.com.jcv.brcities.businesslayer.city;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.brcities.corelayer.specs.AndCriteria;
import br.com.jcv.brcities.corelayer.specs.CityUfSpecification;
import br.com.jcv.brcities.corelayer.specs.EnumCriteriaOperator;
import br.com.jcv.brcities.corelayer.specs.Criteria;
import br.com.jcv.brcities.corelayer.specs.SimpleCriteria;

@Service
public class ListCitiesFromUfBusinessServiceImpl implements ListCitiesFromUfBusinessService{
    private final CityUfSpecification cityUfSpecification;

    public ListCitiesFromUfBusinessServiceImpl(CityUfSpecification cityUfSpecification) {
        this.cityUfSpecification = cityUfSpecification;
    }

    @Override
    public Boolean execute(UUID processId, String uf) {

        // creating filter combination
        SimpleCriteria<String> ufRJCriteria = new SimpleCriteria<>(new Criteria<>("ufId", EnumCriteriaOperator.EQUAL_TO, uf,"CityUf"));
        SimpleCriteria<Long> ufRJLatCriteria = new SimpleCriteria<>(new Criteria<>("latitude", EnumCriteriaOperator.EQUAL_TO, 269L,"CityUf"));
        SimpleCriteria<String> cityNameCriteria = new SimpleCriteria<>(new Criteria<>("name", EnumCriteriaOperator.LIKE,"Volta Redonda", "City"));
        AndCriteria firstAndFilter = new AndCriteria(Arrays.asList(ufRJCriteria,ufRJLatCriteria,cityNameCriteria));

        // Get spec using filter
        Specification<CityUf> allCitiesFromUfUsingFilter = CityUfSpecification.getAllCitiesFromUfUsingFilter(firstAndFilter);
        List<CityUf> allCitiesFromUf = cityUfSpecification.findAll(allCitiesFromUfUsingFilter);

        return Boolean.TRUE;
    }

}

