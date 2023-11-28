package br.com.jcv.security.guardian.controller.v1.business.bootstrap;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;
import br.com.jcv.security.guardian.controller.v1.business.createaccount.CreateNewAccount;
import br.com.jcv.security.guardian.controller.v1.business.createaccount.CreateNewAccountRequest;
import br.com.jcv.security.guardian.controller.v1.business.registerapp.RegisterApplication;
import br.com.jcv.security.guardian.controller.v1.business.registerapp.RegisterApplicationRequest;
import br.com.jcv.security.guardian.controller.v1.business.registerapp.RegisterApplicationResponse;
import br.com.jcv.security.guardian.controller.v1.business.validateaccount.ValidateAccountRequest;
import br.com.jcv.security.guardian.controller.v1.business.validateaccount.ValidateAccountService;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.dto.GroupRoleDTO;
import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.dto.UserRoleDTO;
import br.com.jcv.security.guardian.exception.GApplicationNotFoundException;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
public class InitialGuardianSetupBusinessServiceImpl extends AbstractGuardianBusinessService implements InitialGuardianSetupBusinessService{
    @Autowired private RegisterApplication registerApplicationService;
    @Autowired private CreateNewAccount createNewAccount;
    @Autowired private ValidateAccountService validateAccountService;

    @Override
    public ControllerGenericResponse execute(UUID processId, String magicSeed) {
        log.info("execute :: processId = {} :: has started", processId);
        if(! config.getGuardianAdminMagicSeed().equals(magicSeed)) {
            throw new CommoditieBaseException("Invalid magic seed.", HttpStatus.FORBIDDEN);
        }

        try {
            gApplicationService.findGApplicationByNameAndStatus("GUARDIAN");
            throw new CommoditieBaseException("Guardian application has ever been configured", HttpStatus.FORBIDDEN);
        } catch(GApplicationNotFoundException ignored) {}

        log.info("execute :: processId = {} :: processing Application Guardian entry", processId);
        RegisterApplicationRequest applicationRequest = RegisterApplicationRequest.builder()
                .name("GUARDIAN")
                .jwtTimeToLive(1000000L)
                .build();
        RegisterApplicationResponse applicationResponse = registerApplicationService.execute(processId, applicationRequest);

        log.info("execute :: processId = {} :: processing User Admin entry", processId);
        CreateNewAccountRequest newAccountRequest = CreateNewAccountRequest.builder()
                .externalApplicationUUID(applicationResponse.getExternalCodeUUID())
                .email("admin@gmail.com")
                .name("Guardian Admin")
                .passwd("admin")
                .passwdCheck("admin")
                .birthday(DateUtility.getLocalDate(1,9,2022))
                .build();
        createNewAccount.execute(processId, newAccountRequest);

        log.info("execute :: processId = {} :: processing validate admin account entry", processId);
        ApplicationUserDTO applicationUserDTO = applicationUserService.findApplicationUserByExternalAppUserUUIDAndEmailAndStatus(
                applicationResponse.getExternalCodeUUID(),
                newAccountRequest.getEmail(),
                GenericStatusEnums.PENDENTE.getShortValue()
        );
        ValidateAccountRequest validateAccountRequest = ValidateAccountRequest.builder()
                .externalUUID(applicationUserDTO.getExternalAppUserUUID())
                .requiredCode(applicationUserDTO.getActivationCode())
                .build();
        validateAccountService.execute(processId,validateAccountRequest);


        log.info("execute :: processId = {} :: initializing guardian roles", processId);
        InitializingGuardianRoles(applicationUserDTO,RoleEnums.values());

        ControllerGenericResponse response = ControllerGenericResponse.builder()
                .response( MensagemResponse.builder()
                        .msgcode("GRDN-1502")
                        .mensagem("Your admin account has just been configured.")
                        .build())
                .build();
        log.info("execute :: processId = {} :: has been finished successfully", processId);
        return response;
    }

    private void InitializingGuardianRoles(ApplicationUserDTO applicationUserDTO, RoleEnums[] values) {
        GroupDTO groupAdmin = groupService.salvar(GroupDTO.builder()
                .name("Team Guardian Administrator")
                .build());
        groupService.updateStatusById(groupAdmin.getId(), GenericStatusEnums.ATIVO.getShortValue());

        Arrays.stream(values).forEach(roleItem -> {
            RoleDTO roleDTO = RoleDTO.builder()
                    .name(roleItem.name())
                    .build();
            RoleDTO roleSaved = roleService.salvar(roleDTO);
            roleService.updateStatusById(roleSaved.getId(), GenericStatusEnums.ATIVO.getShortValue());

            UserRoleDTO userRoleSaved = userRoleService.salvar(UserRoleDTO.builder()
                    .idRole(roleSaved.getId())
                    .idUser(applicationUserDTO.getIdUser())
                    .build());
            userRoleService.updateStatusById(userRoleSaved.getId(), GenericStatusEnums.ATIVO.getShortValue());
            GroupRoleDTO groupRoleSaved = groupRoleService.salvar(GroupRoleDTO.builder()
                            .idGroup(groupAdmin.getId())
                            .idRole(roleSaved.getId())
                    .build());
            groupRoleService.updateStatusById(groupRoleSaved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        });
        GroupUserDTO groupUserSaved = groupUserService.salvar(GroupUserDTO.builder()
                .idGroup(groupAdmin.getId())
                .idUser(applicationUserDTO.getIdUser())
                .build());
        groupUserService.updateStatusById(groupUserSaved.getId(),GenericStatusEnums.ATIVO.getShortValue());
    }
}
