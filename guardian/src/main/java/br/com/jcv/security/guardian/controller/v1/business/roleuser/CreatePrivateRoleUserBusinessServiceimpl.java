package br.com.jcv.security.guardian.controller.v1.business.roleuser;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.dto.UserRoleDTO;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CreatePrivateRoleUserBusinessServiceimpl extends AbstractGuardianBusinessService implements CreatePrivateRoleUserBusinessService {
    @Override
    public ControllerGenericResponse execute(UUID processId, String jwtToken, CreatePrivateRoleUserRequest request) {
        log.info("execute :: processId = {} :: has started", processId);

        askHeimdallPermission(jwtToken, RoleEnums.GUARDIAN_CREATE_USER_ROLE_RELATIONSHIP.name());

        ApplicationUserDTO applicationUserDTO = applicationUserService.findApplicationUserByExternalUserUUIDAndStatus(
                request.getExternalUserUUID(), GenericStatusEnums.ATIVO.getShortValue());
        RoleDTO roleDTO = roleService.findRoleByNameAndStatus(request.getRoleName());

        // search for existing role x user relashionship has actived

        UserRoleDTO userRoleDTO = UserRoleDTO.builder()
                .idUser(applicationUserDTO.getIdUser())
                .idRole(roleDTO.getId())
                .build();
        UserRoleDTO urSaved = userRoleService.salvar(userRoleDTO);
        userRoleService.updateStatusById(urSaved.getId(), GenericStatusEnums.ATIVO.getShortValue());

        log.info("execute :: processId = {} :: has been finished", processId);
        return getControllerGenericResponseInstance("GRDN-1303", "User Role relationship has been created.");
    }
}
