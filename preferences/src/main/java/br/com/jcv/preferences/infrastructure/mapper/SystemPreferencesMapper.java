package br.com.jcv.preferences.infrastructure.mapper;

import org.springframework.stereotype.Component;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.preferences.adapter.v1.business.systempreferences.create.CreateSystemPreferencesRequest;
import br.com.jcv.preferences.adapter.v1.business.systempreferences.create.CreateSystemPreferencesResponse;
import br.com.jcv.preferences.infrastructure.dto.SystemPreferencesDTO;

@Component
public class SystemPreferencesMapper implements IPreferencesMapper<CreateSystemPreferencesRequest,
        CreateSystemPreferencesResponse,SystemPreferencesDTO>{

    @Override
    public SystemPreferencesDTO toDTO(CreateSystemPreferencesRequest source) {
        SystemPreferencesDTO dto = new SystemPreferencesDTO();
        dto.setPreference(source.getPreference());
        dto.setKey(source.getKey());
        dto.setUuidExternalApp(source.getUuidExternalApp());
        dto.setPreference(source.getPreference());
        return dto;
    }

    @Override
    public CreateSystemPreferencesResponse getInstance(MensagemResponse mensagemResponse) {
        CreateSystemPreferencesResponse response = new CreateSystemPreferencesResponse();
        response.setMensagemResponse(MensagemResponse.builder()
                    .msgcode(mensagemResponse.getMsgcode())
                    .mensagem(mensagemResponse.getMensagem())
                .build());
        return response;
    }
    @Override
    public CreateSystemPreferencesResponse getInstance(String msgcode, String mensagem) {
        return getInstance(
                MensagemResponse.builder()
                    .msgcode(msgcode)
                    .mensagem(mensagem)
                .build()
        );
    }

}
