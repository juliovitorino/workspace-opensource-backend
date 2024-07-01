package br.com.jcv.brcities.businesslayer.city;

import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.brcities.corelayer.specs.AndCriteria;
import br.com.jcv.brcities.corelayer.specs.CityUfSpecification;
import br.com.jcv.brcities.corelayer.specs.CriteriaClause;
import br.com.jcv.brcities.corelayer.specs.EnumCriteriaOperator;
import br.com.jcv.brcities.corelayer.specs.SimpleCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ListCitiesFromUfBusinessServiceImpl implements ListCitiesFromUfBusinessService{
    private final CityUfSpecification cityUfSpecification;

    public ListCitiesFromUfBusinessServiceImpl(CityUfSpecification cityUfSpecification) {
        this.cityUfSpecification = cityUfSpecification;
    }

    @Override
    public Boolean execute(UUID processId, String uf) {

        // creating filter combination
        SimpleCriteria<String> ufRJCriteria = new SimpleCriteria<>(new CriteriaClause<>("ufId", EnumCriteriaOperator.EQUAL_TO, uf,"CityUf"));
        SimpleCriteria<String> cityNameCriteria = new SimpleCriteria<>(new CriteriaClause<>("name", EnumCriteriaOperator.LIKE,"Volta Redonda", "city"));
        AndCriteria criteriaRjAndVoltaRedonda = new AndCriteria(Arrays.asList(ufRJCriteria,cityNameCriteria));

        // Get GEneric Spec using filter
        Specification<CityUf> allCitiesFromUfUsingFilter = CityUfSpecification.getAllCitiesFromUfUsingFilter(criteriaRjAndVoltaRedonda);
        List<CityUf> allCitiesFromUf = cityUfSpecification.findAll(allCitiesFromUfUsingFilter);

        return Boolean.TRUE;
    }

}

