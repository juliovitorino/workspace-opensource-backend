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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListAllCitiesFromUfBusinessServiceImpl implements ListAllCitiesFromUfBusinessService {

    private final CityUfSpecification cityUfSpecification;
    private final CityUfRepository cityUfRepository;

    public ListAllCitiesFromUfBusinessServiceImpl(CityUfSpecification cityUfSpecification, CityUfRepository cityUfRepository) {
        this.cityUfSpecification = cityUfSpecification;
        this.cityUfRepository = cityUfRepository;
    }

    @Override
    public CitiesResponse execute(UUID processId, String uf) {

        // modo 1
        Specification<CityUf> cityUfSpecByUfId = CityUfSpecification.byUfId(uf);
        List<CityUf> allCities = cityUfRepository.findAll(cityUfSpecByUfId);

        return responseMapper(uf, allCities);
    }

    private CitiesResponse responseMapper(String uf, List<CityUf> allCities) {
        CitiesResponse citiesResponse = new CitiesResponse();
        citiesResponse.setUf(uf);
        List<String> collectCities = allCities.stream().map(cityUfItem -> cityUfItem.getCity().getName()).collect(Collectors.toList());
        citiesResponse.setCityName(collectCities);
        return citiesResponse;
    }
}
