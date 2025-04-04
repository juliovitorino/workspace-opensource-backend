package br.com.jcv.security.guardian.controller.v1.business.validateaccount;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.UsersDTO;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ValidateAccountServiceImpl extends AbstractGuardianBusinessService implements ValidateAccountService {
    @Override
    public ControllerGenericResponse<?> execute(UUID processId, ValidateAccountRequest request) {
        log.info("execute :: processId = {} : has been started", processId);
        ApplicationUserDTO applicationUserDTO =
                applicationUserService.findApplicationUserByExternalAppUserUUIDAndExternalUserUUIDAndStatus(
                        request.getExternalAppUUID(),
                        request.getExternalUserUUID(),
                        GenericStatusEnums.PENDENTE.getShortValue());
        UsersDTO usersDTO = usersService.findById(applicationUserDTO.getIdUser());

        if( ! applicationUserDTO.getActivationCode().equals(request.getRequiredCode())) {
            throw new CommoditieBaseException("Invalid required code", HttpStatus.BAD_REQUEST);
        }

        applicationUserService.updateStatusById(applicationUserDTO.getId(), GenericStatusEnums.ATIVO.getShortValue());
        usersService.updateStatusById(usersDTO.getId(), GenericStatusEnums.ATIVO.getShortValue());

        ApplicationUserDTO updateTo = applicationUserService.findById(applicationUserDTO.getId());
        updateTo.setActivationCode(null);
        updateTo.setDueDateActivation(null);
        updateTo.setUrlTokenActivation(null);
        applicationUserService.salvar(updateTo);

        log.info("execute :: processId = {} : Account has been activated successfully", processId);
        return ControllerGenericResponse.builder()
                .response(
                        MensagemResponse.builder()
                                .msgcode("GRDN-1351")
                                .mensagem(("your account has been activated successfully"))
                                .build()
                )
                .build();
    }
}
