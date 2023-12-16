package br.com.jcv.security.guardian.controller.v1.business.login;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.exception.ApplicationUserNotFoundException;
import br.com.jcv.security.guardian.exception.ApplicationUserWrongEmailException;
import br.com.jcv.security.guardian.exception.GApplicationNotFoundException;
import br.com.jcv.security.guardian.mq.producer.AbstractProducer;
import br.com.jcv.security.guardian.mq.producer.AddNotificationMessage;
import br.com.jcv.security.guardian.mq.producer.IProducer;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginServiceImpl extends AbstractGuardianBusinessService implements LoginService {
    @Override
    public String execute(UUID processId, LoginRequest loginRequest) {


        ApplicationUserDTO applicationUserDTO = null;
        try {
            gApplicationService.findGApplicationByExternalCodeUUIDAndStatus(loginRequest.getApplicationExternalUUID());
            applicationUserDTO = applicationUserService.findApplicationUserByExternalAppUserUUIDAndEmailAndStatus(
                    loginRequest.getApplicationExternalUUID(),
                    loginRequest.getEmail(),
                    GenericStatusEnums.ATIVO.getShortValue());

        } catch (ApplicationUserNotFoundException e) {
            throw new ApplicationUserWrongEmailException("Permission denied. Wrong email", HttpStatus.FORBIDDEN);
        } catch(GApplicationNotFoundException e) {
            throw new CommoditieBaseException("Permission denied. Invalid Application Code. Check it out with administrator.", HttpStatus.FORBIDDEN);
        }

        GApplicationDTO appdto = gApplicationService.findById(applicationUserDTO.getIdApplication());

        if( ! getMD5HashFromString(loginRequest.getCodePass()).equals(applicationUserDTO.getEncodedPassPhrase())) {
            throw new CommoditieBaseException("Access denied. Invalid user or password", HttpStatus.FORBIDDEN);
        }

        String tokenSessionId = createSession(applicationUserDTO);
        return guardianJwtService.createJwtToken(tokenSessionId, appdto.getJwtTimeToLive());
    }


    private String createSession(ApplicationUserDTO applicationUserDTO) {
        UUID idToken = UUID.randomUUID();
        SessionStateDTO sessionStateDTO = new SessionStateDTO();
        sessionStateDTO.setIdToken(idToken);
        sessionStateDTO.setIdUserUUID(applicationUserDTO.getExternalUserUUID());

        SessionStateDTO saved = sessionStateService.salvar(sessionStateDTO);
        sessionStateService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return idToken.toString();
    }


}
