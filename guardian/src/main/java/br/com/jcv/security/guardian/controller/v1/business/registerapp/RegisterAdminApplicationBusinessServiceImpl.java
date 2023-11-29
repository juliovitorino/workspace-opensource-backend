package br.com.jcv.security.guardian.controller.v1.business.registerapp;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import br.com.jcv.security.guardian.service.GApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RegisterAdminApplicationBusinessServiceImpl
        extends AbstractGuardianBusinessService
        implements RegisterAdminApplication {

    @Autowired private GApplicationService gApplicationService;

    @Override
    public RegisterApplicationResponse execute(UUID processId, RegisterApplicationAdminRequest request) {
        if(! request.getMagicSeed().equals(config.getGuardianAdminMagicSeed())) {
            throw new CommoditieBaseException("Invalid magic seed", HttpStatus.FORBIDDEN);
        }

        UUID uuidApp = UUID.randomUUID();
        GApplicationDTO dto = new GApplicationDTO();
        dto.setName(request.getName());
        dto.setExternalCodeUUID(uuidApp);
        dto.setJwtTimeToLive(request.getJwtTimeToLive());

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
