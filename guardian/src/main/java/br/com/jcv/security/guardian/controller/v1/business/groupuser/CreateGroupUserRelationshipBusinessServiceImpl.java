package br.com.jcv.security.guardian.controller.v1.business.groupuser;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.exception.GroupUserNotFoundException;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import br.com.jcv.security.guardian.service.GApplicationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CreateGroupUserRelationshipBusinessServiceImpl extends AbstractGuardianBusinessService implements CreateGroupUserRelationshipBusinessService {

    @Autowired private GApplicationService gApplicationService;

    @Override
    public ControllerGenericResponse execute(UUID processId, String jwtToken, CreateGroupUserRequest request) {
        log.info("execute :: processId = {} :: has started", processId);
        askHeimdallPermission(jwtToken, RoleEnums.GUARDIAN_CREATE_GROUP_USER_RELATIONSHIP.name());

        GApplicationDTO applicationDTO = gApplicationService.findGApplicationByExternalCodeUUIDAndStatus(request.getExternalAppUUID(), GenericStatusEnums.ATIVO.getShortValue());

        ApplicationUserDTO applicationUserDTO = applicationUserService.findApplicationUserByExternalUserUUIDAndStatus(
                request.getExternalUserUUID(), GenericStatusEnums.ATIVO.getShortValue());
        GroupDTO groupDTO = groupService.findGroupByNameAndStatus(request.getGroupName());

        if(!applicationDTO.getExternalCodeUUID().equals(applicationUserDTO.getExternalAppUserUUID())) {
            throw new CommoditieBaseException("Application code is invalid", HttpStatus.BAD_REQUEST);
        }

        try {
            groupUserService.findGroupUserByIdGroupAndIdUserAndStatus(
                    groupDTO.getId(),
                    applicationUserDTO.getIdUser(),
                    GenericStatusEnums.ATIVO.getShortValue());
            throw new CommoditieBaseException("User Group relationship has already activated", HttpStatus.BAD_REQUEST);
        } catch (GroupUserNotFoundException ignored) {}

        GroupUserDTO groupUserDTO = GroupUserDTO.builder()
                .idUser(applicationUserDTO.getIdUser())
                .idGroup(groupDTO.getId())
                .build();
        GroupUserDTO guSaved = groupUserService.salvar(groupUserDTO);
        groupUserService.updateStatusById(guSaved.getId(), GenericStatusEnums.ATIVO.getShortValue());

        log.info("execute :: processId = {} :: has been finished", processId);
        return getControllerGenericResponseInstance("GRDN-1050", "Relationship between group and user has been created");
    }
}
