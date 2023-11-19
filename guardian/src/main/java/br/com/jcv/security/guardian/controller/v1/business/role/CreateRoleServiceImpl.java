package br.com.jcv.security.guardian.controller.v1.business.role;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CreateRoleServiceImpl extends AbstractGuardianBusinessService implements CreateRoleService{
    @Override
    public RoleResponse execute(UUID processId, String jwtToken, RoleRequest roleRequest) {
        askHeimdallPermission(jwtToken, "GUARDIAN_CREATE_ROLE");

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(roleRequest.getName());
        RoleDTO saved = roleService.salvar(roleDTO);
        roleService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());

        RoleResponse response = new RoleResponse();
        response.setResponse(MensagemResponse.builder()
                        .msgcode("GRDN-1602")
                        .mensagem("This role has been created")
                .build());

        return response;
    }

}
