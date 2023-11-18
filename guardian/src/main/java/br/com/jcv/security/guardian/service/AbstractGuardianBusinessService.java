package br.com.jcv.security.guardian.service;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.exception.ApplicationUserNotFoundException;
import br.com.jcv.security.guardian.exception.SessionStateNotFoundException;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.UUID;

@Component
public abstract class AbstractGuardianBusinessService {
    @Autowired protected Gson gson;
    @Autowired protected SessionStateService sessionStateService;
    @Autowired protected UsersService usersService;
    @Autowired protected GApplicationService gApplicationService;
    @Autowired protected ApplicationUserService applicationUserService;
    @Autowired protected GuardianJwtService guardianJwtService;
    @Autowired protected DateTime dateTime;

    protected void validatePermission(String jwtToken, String roleAllowed) {
        Jws<Claims> claimsJws = guardianJwtService.parseJwt(jwtToken);
        String sessionId = claimsJws.getBody().getSubject();
        SessionStateDTO sessionDTO = null;
        ApplicationUserDTO applicationUserDTO = null;
        try {
            sessionDTO = sessionStateService.findSessionStateByIdTokenAndStatus(UUID.fromString(sessionId));
            applicationUserDTO = applicationUserService.findApplicationUserByExternalUserUUIDAndStatus(
                    sessionDTO.getIdUserUUID(),
                    GenericStatusEnums.ATIVO.getShortValue());
        } catch(SessionStateNotFoundException e) {
            throw new CommoditieBaseException("Your session has been removed. Try a new login.", HttpStatus.FORBIDDEN);
        } catch(ApplicationUserNotFoundException e) {
            throw new CommoditieBaseException("Your application user is invalid. Try a new login.", HttpStatus.FORBIDDEN);
        }
    }
    protected boolean validateUserSession(String jwtToken) {
        Jws<Claims> claimsJws = guardianJwtService.parseJwt(jwtToken);
        System.out.println(gson.toJson(claimsJws));
        return true;
    }

    protected String getMD5HashFromString(String seed) {
        return DigestUtils.md5Hex(seed).toUpperCase();
    }
}
