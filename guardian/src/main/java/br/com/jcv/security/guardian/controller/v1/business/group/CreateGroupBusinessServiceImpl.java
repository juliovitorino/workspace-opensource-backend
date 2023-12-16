package br.com.jcv.security.guardian.controller.v1.business.group;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;
import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.exception.GroupNotFoundException;
import br.com.jcv.security.guardian.mq.producer.AddNotificationMessage;
import br.com.jcv.security.guardian.mq.producer.AddNotifierRequest;
import br.com.jcv.security.guardian.mq.producer.IProducer;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
public class CreateGroupBusinessServiceImpl extends AbstractGuardianBusinessService implements CreateGroupBusinessService{
    @Autowired private @Qualifier("notifierProducer") IProducer<AddNotificationMessage,Boolean> notifierProducer;
    @Override
    public ControllerGenericResponse execute(UUID processId, String jwtToken, GroupRequest request) {
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

        notifierProducer.dispatch(getNotificationMessage(processId, askHeimdallPermission(jwtToken), groupDTO));
        return getControllerGenericResponseInstance("GRDN-1227","Your group has been created successfully");

    }

    private AddNotificationMessage getNotificationMessage(UUID processId, SessionStateDTO sessionStateDTO, GroupDTO groupDTO) {
        AddNotificationMessage response = AddNotificationMessage.builder()
                .processId(processId)
                .addNotificationRequest( AddNotifierRequest.builder()
                        .applicationUUID(sessionStateDTO.getIdUserUUID())
                        .userUUID(sessionStateDTO.getIdUserUUID())
                        .type("SM")
                        .title("Create group " + groupDTO.getName())
                        .description("Group has been created")
                        .isReaded("N")
                        .build()
                )
                .build();
        return response;
    }
}
