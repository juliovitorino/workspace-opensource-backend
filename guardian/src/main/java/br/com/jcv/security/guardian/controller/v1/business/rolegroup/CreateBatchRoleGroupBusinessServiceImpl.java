package br.com.jcv.security.guardian.controller.v1.business.rolegroup;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreateBatchRoleGroupBusinessServiceImpl implements CreateBatchRoleGroupBusinessService {

    @Autowired private CreateRoleGroupBusinessService createRoleGroupBusinessService;

    @Override
    public MensagemResponse execute(UUID processId, String jwtToken, CreateBatchRoleGroupRequest createBatchRoleGroupRequest) {
        createBatchRoleGroupRequest.getRoleName().forEach(roleItem -> createRoleGroupBusinessService.execute(
                processId,
                jwtToken,
                CreateRoleGroupRequest.builder()
                        .groupName(createBatchRoleGroupRequest.getGroupName())
                        .roleName(roleItem)
                        .build()));
        return MensagemResponse.builder()
                .msgcode("GRDN-0001")
                .mensagem("Batch role group relationship has been created.")
                .build();
    }
}
