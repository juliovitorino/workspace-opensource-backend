package br.com.jcv.security.guardian.controller.v1.business.group;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;
import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.exception.GroupNotFoundException;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
public class CreateGroupBusinessServiceImpl extends AbstractGuardianBusinessService implements CreateGroupBusinessService{
    @Override
    public ControllerGenericResponse<GroupDTO> execute(UUID processId, String jwtToken, GroupRequest request) {
        askHeimdallPermission(jwtToken, RoleEnums.GUARDIAN_CREATE_GROUP.name());

        try {
            groupService.findGroupByNameAndStatus(request.getName().toUpperCase());
            throw new CommoditieBaseException("This group has already exist.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch(GroupNotFoundException ignored) {}

        GroupDTO groupDTO = GroupDTO.builder()
                .name(request.getName().toUpperCase())
                .build();
        GroupDTO groupSaved = groupService.salvar(groupDTO);
        groupService.updateStatusById(groupSaved.getId(), GenericStatusEnums.ATIVO.getShortValue());

        ControllerGenericResponse<GroupDTO> groupResponse = new ControllerGenericResponse<>();
        groupResponse.setObjectResponse(groupSaved);
        groupResponse.setResponse(MensagemResponse.builder()
                .msgcode("GRDN-1227")
                .mensagem("Your group has been created successfully")
                .build());
        return groupResponse;
    }
}
