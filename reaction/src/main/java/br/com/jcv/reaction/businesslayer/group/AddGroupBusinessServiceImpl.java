package br.com.jcv.reaction.businesslayer.group;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.helper.MensagemResponseHelperService;
import br.com.jcv.reaction.adapter.controller.v1.business.group.GroupRequest;
import br.com.jcv.reaction.corelayer.service.GroupService;
import br.com.jcv.reaction.infrastructure.dto.GroupDTO;
import br.com.jcv.reaction.infrastructure.exception.GroupNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddGroupBusinessServiceImpl implements AddGroupBusinessService {

    private final GroupService groupService;

    @Override
    public MensagemResponse execute(UUID processId, GroupRequest groupRequest) {

        try{
            groupService.findGroupByNameAndStatus(groupRequest.getName(), GenericStatusEnums.ATIVO.getShortValue());
            throw new CommoditieBaseException("Reaction Groupname has been found.", HttpStatus.BAD_REQUEST, "ERR-1054");
        } catch (GroupNotFoundException ignored) {
            log.info("execute :: No active reaction groupname has been found... keep going.");
        }

        GroupDTO saved = groupService.salvar(this.toDto(groupRequest));
        groupService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return MensagemResponseHelperService.getInstance("RECT-0001", "Group has been included");
    }

    private GroupDTO toDto(GroupRequest request) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName(request.getName());
        return groupDTO;
    }
}
