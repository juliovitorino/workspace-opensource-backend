package br.com.jcv.brcities.businesslayer.city;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.jcv.brcities.adapter.controller.v1.business.city.CitiesResponse;
import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.brcities.corelayer.repository.CityUfRepository;
import br.com.jcv.brcities.corelayer.specs.CityUfSpecification;
import br.com.jcv.brcities.corelayer.specs.CriteriaClause;
import br.com.jcv.brcities.corelayer.specs.EnumCriteriaOperator;
import br.com.jcv.brcities.corelayer.specs.SimpleCriteria;

@Service
public class ListCitiesFromUfBusinessServiceImpl implements ListCitiesFromUfBusinessService{
    private final CityUfSpecification cityUfSpecification;
    private final CityUfRepository cityUfRepository;

    public ListCitiesFromUfBusinessServiceImpl(CityUfSpecification cityUfSpecification, CityUfRepository cityUfRepository) {
        this.cityUfSpecification = cityUfSpecification;
        this.cityUfRepository = cityUfRepository;
    }

    @Override
    public CitiesResponse execute(UUID processId, String uf) {

        // creating filter combination sample
//        SimpleCriteria<String> ufRJCriteria = new SimpleCriteria<>(new CriteriaClause<>("ufId", EnumCriteriaOperator.EQUAL_TO, uf,"CityUf"));
//        SimpleCriteria<String> cityNameCriteria = new SimpleCriteria<>(new CriteriaClause<>("name", EnumCriteriaOperator.LIKE,"Volta Redonda", "city"));
//        AndCriteria criteriaRjAndVoltaRedonda = new AndCriteria(Arrays.asList(ufRJCriteria,cityNameCriteria));

        SimpleCriteria<String> ufRJCriteria = new SimpleCriteria<>(new CriteriaClause<>("ufId", EnumCriteriaOperator.EQUAL_TO, uf,"CityUf"));

        // Get GEneric Spec using filter
//        Specification<CityUf> allCitiesFromUfUsingFilter = CityUfSpecification.getAllCitiesFromUfUsingFilter(criteriaRjAndVoltaRedonda);
        Specification<CityUf> allCitiesFromUfUsingFilter = CityUfSpecification.getAllCitiesFromUfUsingFilter(ufRJCriteria);
        List<CityUf> allCitiesFromUf = cityUfRepository.findAll(allCitiesFromUfUsingFilter);

        return responseMapper(uf, allCitiesFromUf);
    }

    private CitiesResponse responseMapper(String uf, List<CityUf> allCities) {
        CitiesResponse citiesResponse = new CitiesResponse();
        citiesResponse.setUf(uf);
        List<String> collectCities = allCities.stream().map(cityUfItem -> cityUfItem.getCity().getName()).collect(Collectors.toList());
        citiesResponse.setCityName(collectCities);
        return citiesResponse;
    }
}

