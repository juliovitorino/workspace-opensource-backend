package br.com.jcv.security.guardian.controller.v1.business.registerapp;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.service.GApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RegisterApplicationBusinessServiceImpl implements RegisterApplication{

    @Autowired private GApplicationService gApplicationService;
    public RegisterApplicationResponse execute(UUID processId, RegisterApplicationRequest request) {
        UUID uuidApp = UUID.randomUUID();
        GApplicationDTO dto = new GApplicationDTO();
        dto.setName(request.getName());
        dto.setExternalCodeUUID(uuidApp);

        GApplicationDTO savedApp = gApplicationService.salvar(dto);
        gApplicationService.updateStatusById(savedApp.getId(), GenericStatusEnums.ATIVO.getShortValue());

        RegisterApplicationResponse response = new RegisterApplicationResponse();
        response.setExternalCodeUUID(uuidApp);
        response.setResponse(MensagemResponse.builder()
                        .msgcode("GRDN-1228")
                        .mensagem("your application has been created successfully. This code should be used to create a new user.")
                .build());
        return response;
    }
}
