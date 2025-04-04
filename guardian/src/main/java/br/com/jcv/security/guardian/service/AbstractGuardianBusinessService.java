package br.com.jcv.security.guardian.service;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.security.guardian.RoleEnums;
import br.com.jcv.security.guardian.config.GuardianConfig;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.GroupRoleDTO;
import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.dto.UserRoleDTO;
import br.com.jcv.security.guardian.exception.ApplicationUserNotFoundException;
import br.com.jcv.security.guardian.exception.RoleNotFoundException;
import br.com.jcv.security.guardian.exception.SessionStateNotFoundException;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public abstract class AbstractGuardianBusinessService {
    @Autowired protected Gson gson;
    @Autowired protected @Qualifier("redisService") CacheProvider redis;
    @Autowired protected GuardianConfig config;
    @Autowired protected SessionStateService sessionStateService;
    @Autowired protected UsersService usersService;
    @Autowired protected GApplicationService gApplicationService;
    @Autowired protected ApplicationUserService applicationUserService;
    @Autowired protected RoleService roleService;
    @Autowired protected UserRoleService userRoleService;
    @Autowired protected GroupService groupService;
    @Autowired protected GroupRoleService groupRoleService;
    @Autowired protected GroupUserService groupUserService;
    @Autowired protected GuardianJwtService guardianJwtService;
    @Autowired protected DateTime dateTime;

    protected SessionStateDTO askHeimdallPermission(String jwtToken) {
        Jws<Claims> claimsJws = guardianJwtService.parseJwt(jwtToken);
        String sessionId = claimsJws.getBody().getSubject();
        SessionStateDTO sessionDTO;
        try {
            sessionDTO = sessionStateService.findSessionStateByIdTokenAndStatus(UUID.fromString(sessionId));
        } catch(SessionStateNotFoundException e) {
            throw new CommoditieBaseException("Your session has been removed. Try a new login.", HttpStatus.FORBIDDEN);
        }
        return sessionDTO;
    }
    protected void askHeimdallPermission(String jwtToken, String roleAllowed) {
        SessionStateDTO sessionDTO = askHeimdallPermission(jwtToken);
        ApplicationUserDTO applicationUserDTO;
        RoleDTO roleDTO ;
        try {
            applicationUserDTO = applicationUserService.findApplicationUserByExternalUserUUIDAndStatus(
                    sessionDTO.getIdUserUUID(),
                    GenericStatusEnums.ATIVO.getShortValue());
            roleDTO = roleService.findRoleByNameAndStatus(roleAllowed);
        } catch(SessionStateNotFoundException e) {
            throw new CommoditieBaseException("Your session has been removed. Try a new login.", HttpStatus.FORBIDDEN);
        } catch(ApplicationUserNotFoundException e) {
            throw new CommoditieBaseException(
                    "Your application user is invalid. call the system administrator to check it out your permission.",
                    HttpStatus.FORBIDDEN);
        } catch (RoleNotFoundException e) {
            throw new CommoditieBaseException("Invalid role " + roleAllowed + " or it hasn't been activated", HttpStatus.FORBIDDEN);
        }

        if(hasSuperPower(applicationUserDTO.getIdUser())) return;

        List<UserRoleDTO> userRoleDTOList =
                userRoleService.findAllUserRoleByIdUserAndIdRoleAndStatus(
                        applicationUserDTO.getIdUser(),
                        roleDTO.getId(),
                        GenericStatusEnums.ATIVO.getShortValue());
        UserRoleDTO userRoleFilter = userRoleDTOList.stream()
                .filter(item -> Objects.equals(item.getIdRole(), roleDTO.getId()))
                .findFirst()
                .orElse(null);

        boolean groupRoleCheckNotFound = true;
        List<GroupRoleDTO> groupRoleDTOList = groupRoleService.findAllGroupRoleByIdRoleAndStatus(
                roleDTO.getId(), GenericStatusEnums.ATIVO.getShortValue());
        for(GroupRoleDTO grItem : groupRoleDTOList) {
            List<GroupUserDTO> groupUserDTOList = groupUserService.findAllGroupUserByIdGroupAndIdUserAndStatus(
                    grItem.getIdGroup(),
                    applicationUserDTO.getIdUser(),
                    GenericStatusEnums.ATIVO.getShortValue());
            if(!groupUserDTOList.isEmpty()){
                groupRoleCheckNotFound = false;
                break;
            }
        }

        if(Objects.isNull(userRoleFilter) && groupRoleCheckNotFound) {
            throw new CommoditieBaseException(
                    "Heimdall checking. You have no private permission or Group permission to execute this role.",
                    HttpStatus.FORBIDDEN);
        }


    }

    private boolean hasSuperPower(Long idUser) {
        RoleDTO superPowerRole = roleService.findRoleByNameAndStatus(RoleEnums.ADMIN_SUPER_POWER.name());
        List<UserRoleDTO> userWithSuperPower =
                userRoleService.findAllUserRoleByIdUserAndIdRoleAndStatus(
                        idUser,
                        superPowerRole.getId(),
                        GenericStatusEnums.ATIVO.getShortValue());
        return ! userWithSuperPower.isEmpty();
    }

    protected String getMD5HashFromString(String seed) {
        return DigestUtils.md5Hex(seed).toUpperCase();
    }

    protected ControllerGenericResponse getControllerGenericResponseInstance(String msgcode, String msg) {
        return ControllerGenericResponse.builder()
                .response(MensagemResponse.builder()
                        .msgcode(msgcode)
                        .mensagem(msg)
                        .build())
                .build();
    }
}
