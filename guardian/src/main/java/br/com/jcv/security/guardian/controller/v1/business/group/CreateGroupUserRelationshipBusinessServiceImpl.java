package br.com.jcv.security.guardian.controller.v1.business.group;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CreateGroupUserRelationshipBusinessServiceImpl extends AbstractGuardianBusinessService implements CreateGroupUserRelationshipBusinessService {
    @Override
    public GroupUserResponse execute(UUID processId, String jwtToken, GroupUserRequest request) {
        askHeimdallPermission(jwtToken, "GUARDIAN_CREATE_GROUP_USER_RELATIONSHIP");
        GroupUserDTO groupUserDTO = new GroupUserDTO();
        groupUserDTO.setIdUser(request.getIdUser());
        groupUserDTO.setIdGroup(request.getIdGroup());
        GroupUserDTO saved = groupUserService.salvar(groupUserDTO);
        groupUserService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return null;
    }
}
