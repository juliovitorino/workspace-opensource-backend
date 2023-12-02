package br.com.jcv.security.guardian.controller.v1.business.rolegroup;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;
import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.dto.GroupRoleDTO;
import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.exception.GroupRoleNotFoundException;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CreateRoleGroupBusinessServiceImpl extends AbstractGuardianBusinessService implements CreateRoleGroupBusinessService {
    @Override
    public ControllerGenericResponse execute(UUID processId, String jwtToken, CreateRoleGroupRequest request) {
        log.info("execute :: processId = {} :: has started", processId);

        askHeimdallPermission(jwtToken, RoleEnums.GUARDIAN_CREATE_GROUP_ROLE_RELATIONSHIP.name());

        GroupDTO groupDTO = groupService.findGroupByNameAndStatus(request.getGroupName());
        RoleDTO roleDTO = roleService.findRoleByNameAndStatus(request.getRoleName());

        try {
            groupRoleService.findGroupRoleByIdGroupAndIdRoleAndStatus(groupDTO.getId(), roleDTO.getId(), GenericStatusEnums.ATIVO.getShortValue());
            throw new CommoditieBaseException("Group and Role have already activated.", HttpStatus.BAD_REQUEST);
        } catch (GroupRoleNotFoundException ignored) {}

        GroupRoleDTO grSaved = groupRoleService.salvar(GroupRoleDTO.builder()
                .idRole(roleDTO.getId())
                .idGroup(groupDTO.getId())
                .build());
        groupRoleService.updateStatusById(grSaved.getId(), GenericStatusEnums.ATIVO.getShortValue());

        log.info("execute :: processId = {} :: has been finished started", processId);
        return getControllerGenericResponseInstance("GRDN-1611","Group and role relationship has been created.");
    }
}
