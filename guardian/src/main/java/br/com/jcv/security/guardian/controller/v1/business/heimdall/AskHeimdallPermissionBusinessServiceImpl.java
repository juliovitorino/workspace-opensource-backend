package br.com.jcv.security.guardian.controller.v1.business.heimdall;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AskHeimdallPermissionBusinessServiceImpl extends AbstractGuardianBusinessService implements AskHeimdallPermissionBusinessService {
    @Override
    public ControllerGenericResponse execute(UUID processId, String jwtToken, String permission) {
        askHeimdallPermission(jwtToken,permission);

        return getControllerGenericResponseInstance("GRDN-1202","Your access has been granted.");
    }
}
