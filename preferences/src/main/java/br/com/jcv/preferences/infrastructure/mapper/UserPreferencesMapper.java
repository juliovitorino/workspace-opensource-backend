package br.com.jcv.preferences.infrastructure.mapper;

import org.springframework.stereotype.Component;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.preferences.adapter.v1.business.userpreferences.create.CreateUserPreferencesRequest;
import br.com.jcv.preferences.adapter.v1.business.userpreferences.create.CreateUserPreferencesResponse;
import br.com.jcv.preferences.infrastructure.dto.UserPreferencesDTO;

@Component
public class UserPreferencesMapper {

    public <T extends CreateUserPreferencesRequest> UserPreferencesDTO toDTO(T source) {
        UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO();
        userPreferencesDTO.setPreference(source.getPreference());
        userPreferencesDTO.setKey(source.getKey());
        userPreferencesDTO.setUuidExternalUser(source.getUuidExternalUser());
        userPreferencesDTO.setUuidExternalApp(source.getUuidExternalApp());
        userPreferencesDTO.setPreference(source.getPreference());
        return userPreferencesDTO;

    }

    public CreateUserPreferencesResponse getInstance(MensagemResponse mensagemResponse) {
        CreateUserPreferencesResponse createUserPreferencesResponse = new CreateUserPreferencesResponse();
        createUserPreferencesResponse.setMensagemResponse(MensagemResponse.builder()
                    .msgcode(mensagemResponse.getMsgcode())
                    .mensagem(mensagemResponse.getMensagem())
                .build());
        return createUserPreferencesResponse;
    }
    public CreateUserPreferencesResponse getInstance(String msgcode, String mensagem) {
        return getInstance(
                MensagemResponse.builder()
                    .msgcode(msgcode)
                    .mensagem(mensagem)
                .build()
        );
    }
}
