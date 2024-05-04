package br.com.jcv.preferences.corebusiness.systempreferences.create;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.preferences.adapter.v1.business.systempreferences.create.CreateSystemPreferencesRequest;
import br.com.jcv.preferences.adapter.v1.business.systempreferences.create.CreateSystemPreferencesResponse;
import br.com.jcv.preferences.corelayer.service.SystemPreferencesService;
import br.com.jcv.preferences.infrastructure.dto.SystemPreferencesDTO;
import br.com.jcv.preferences.infrastructure.mapper.SystemPreferencesMapper;
import br.com.jcv.preferences.infrastructure.restclient.guardian.GuardianRestClientConsumer;

@Service
public class CreateSystemPreferencesBusinessServiceImpl implements CreateSystemPreferencesBusinessService {

    @Autowired private SystemPreferencesService systemPreferencesService;
    @Autowired private GuardianRestClientConsumer guardianRestClientConsumer;
    @Autowired private SystemPreferencesMapper mapper;

    @Override
    public CreateSystemPreferencesResponse execute(UUID processId, CreateSystemPreferencesRequest createSystemPreferencesRequest) {
        guardianRestClientConsumer.validateApplicationCode(createSystemPreferencesRequest.getUuidExternalApp());
        SystemPreferencesDTO systemPreferencesDTO = mapper.toDTO(createSystemPreferencesRequest);
        SystemPreferencesDTO saved = systemPreferencesService.salvar(systemPreferencesDTO);
        systemPreferencesService.updateStatusById(saved.getId(), "A");
        return mapper.getInstance("MSG-0001","Your system preference has been created");
    }
}
