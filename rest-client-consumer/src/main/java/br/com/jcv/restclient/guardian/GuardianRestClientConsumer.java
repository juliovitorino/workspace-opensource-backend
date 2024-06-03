package br.com.jcv.restclient.guardian;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.restclient.guardian.request.CreateBatchRoleGroupRequest;
import br.com.jcv.restclient.guardian.request.CreateGroupUserRequest;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import br.com.jcv.restclient.guardian.request.CreatePrivateRoleUserRequest;
import br.com.jcv.restclient.guardian.request.CreateRoleGroupRequest;
import br.com.jcv.restclient.guardian.request.GroupRequest;
import br.com.jcv.restclient.guardian.request.RegisterApplicationRequest;
import br.com.jcv.restclient.guardian.request.RoleRequest;
import br.com.jcv.restclient.guardian.request.ValidateAccountRequest;
import br.com.jcv.restclient.guardian.response.CreateNewAccountResponse;
import br.com.jcv.restclient.guardian.response.RegisterApplicationResponse;
import br.com.jcv.restclient.guardian.response.RoleResponse;
import br.com.jcv.restclient.guardian.response.ValidateAccountResponse;
import br.com.jcv.restclient.infrastructure.response.ControllerGenericResponse;

public interface GuardianRestClientConsumer {
    String login(LoginRequest request) ;
    ControllerGenericResponse askHeimdallPermission(String jwtToken,String role);
    RegisterApplicationResponse registerApp(RegisterApplicationRequest request,String jwtToken);

    CreateNewAccountResponse createAccount(CreateNewAccountRequest request);
    ControllerGenericResponse createGroup(String jwtToken, GroupRequest request);
    ControllerGenericResponse createGroupUserRelationship(String jwtToken, CreateGroupUserRequest request);
    RoleResponse createRole(RoleRequest request, String jwtToken);
    ControllerGenericResponse createRoleGroupRelationship(String jwtToken, CreateRoleGroupRequest request);
    MensagemResponse createBatchRoleGroup(String jwtToken, CreateBatchRoleGroupRequest request);
    ControllerGenericResponse createRoleUserRelationship(String jwtToken, CreatePrivateRoleUserRequest request);
    ValidateAccountResponse validateAccount(UUID externalAppUUID, ValidateAccountRequest request);
}

