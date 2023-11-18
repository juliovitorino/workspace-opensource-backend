package br.com.jcv.security.guardian.controller.v1.business.login;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.exception.ApplicationUserNotFoundException;
import br.com.jcv.security.guardian.exception.ApplicationUserWrongEmailException;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class LoginServiceImpl extends AbstractGuardianBusinessService implements LoginService {
    @Override
    public String execute(UUID processId, LoginRequest loginRequest) {

        gApplicationService.findGApplicationByExternalCodeUUIDAndStatus(loginRequest.getApplicationExternalUUID());

        ApplicationUserDTO applicationUserDTO = null;
        try {
                applicationUserDTO = applicationUserService.findApplicationUserByExternalAppUserUUIDAndEmailAndStatus(
                        loginRequest.getApplicationExternalUUID(),
                        loginRequest.getEmail(),
                        GenericStatusEnums.ATIVO.getShortValue());

        } catch (ApplicationUserNotFoundException e) {
            throw new ApplicationUserWrongEmailException("Permission denied. Wrong email", HttpStatus.FORBIDDEN);
        }

        if( ! getMD5HashFromString(loginRequest.getCodePass()).equals(applicationUserDTO.getEncodedPassPhrase())) {
            throw new CommoditieBaseException("Access denied. Invalid user or password", HttpStatus.FORBIDDEN);
        }

        String tokenSessionId = createSession(applicationUserDTO);
        return guardianJwtService.createJwtToken(tokenSessionId);
    }

//    private String guardianJwtToken(String tokenSessionId) {
//        final String seed = "LvqdFVwpliVGDyOH2imXAlrf5ni09doCEYv8myS8XkXORkAlCc";
//        final long EXPIRATION_TIME = 2000;
//        final Date now = dateTime.getToday();
//        final Date expirationDate = DateUtility.getDate(now.getTime() + EXPIRATION_TIME);
//
//        return Jwts.builder()
//                .setSubject(tokenSessionId)
//                .setId(UUID.randomUUID().toString())
//                .setIssuedAt(dateTime.getToday())
//                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS256,seed)
//                .compact();
//    }

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
