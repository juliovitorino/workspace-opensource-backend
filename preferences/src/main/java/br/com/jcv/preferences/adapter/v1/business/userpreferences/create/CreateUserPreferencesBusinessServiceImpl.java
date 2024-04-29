package br.com.jcv.preferences.adapter.v1.business.userpreferences.create;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.preferences.corelayer.service.UserPreferencesService;
import br.com.jcv.preferences.infrastructure.dto.UserPreferencesDTO;
import br.com.jcv.preferences.infrastructure.mapper.UserPreferencesMapper;

@Service
public class CreateUserPreferencesBusinessServiceImpl implements CreateUserPreferencesBusinessService {

    @Autowired private UserPreferencesService userPreferencesService;
    @Autowired private UserPreferencesMapper mapper;

    @Override
    public CreateUserPreferencesResponse execute(UUID processId, CreateUserPreferencesRequest createUserPreferencesRequest) {
        UserPreferencesDTO userPreferencesDTO = mapper.toDTO(createUserPreferencesRequest);
        UserPreferencesDTO saved = userPreferencesService.salvar(userPreferencesDTO);
        userPreferencesService.updateStatusById(saved.getId(), "A");
        return mapper.getInstance("MSG-0001","Your user preference has been created");
    }
}
