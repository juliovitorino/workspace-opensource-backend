package br.com.jcv.brcities.businesslayer.city;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jcv.brcities.adapter.controller.v1.business.city.CitiesResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListAllCitiesFromUfBusinessServiceImpl implements ListAllCitiesFromUfBusinessService {
    @Override
    public CitiesResponse execute(UUID processId, String uf) {
        return null;
    }
}
