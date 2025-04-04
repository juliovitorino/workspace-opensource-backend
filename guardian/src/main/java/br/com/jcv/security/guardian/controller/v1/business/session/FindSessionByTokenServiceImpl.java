package br.com.jcv.security.guardian.controller.v1.business.session;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindSessionByTokenServiceImpl extends AbstractGuardianBusinessService implements FindSessionByTokenService {

    @Override
    @Transactional
    public ControllerGenericResponse<SessionStateDTO> execute(UUID processId, String token) {
        SessionStateDTO sessionStateDTO = askHeimdallPermission(token);
        ControllerGenericResponse<SessionStateDTO> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                        .msgcode("MSG-0001")
                        .mensagem("Command has been execute sucessfully!")
                .build());
        response.setObjectResponse(sessionStateDTO);
        return response;
    }
}
