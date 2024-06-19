package br.com.jcv.restclient.guardian;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.http.HttpURLConnection;
import br.com.jcv.restclient.exception.GuardianRestClientConsumerException;
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
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GuardianRestClientConsumerImpl implements GuardianRestClientConsumer {

    public static final String GUARDIAN_MICROSERVICE_ERROR = "Guardian Microservice error";

    private final HttpURLConnection httpURLConnection;

    static final String GUARDIAN_SERVICE_URL = "http://localhost:8080/v1/api/business";

    @Override
    public String login(@RequestBody LoginRequest request) {

        String jwtToken;
        try {
            jwtToken = httpURLConnection.sendPOST(GUARDIAN_SERVICE_URL + "/login", request, String.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException("Guardian Integration error",HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return jwtToken;
    }

    @Override
    public ControllerGenericResponse askHeimdallPermission(String jwtToken, String role) {

        ControllerGenericResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                        GUARDIAN_SERVICE_URL + "/heimdall",
                        Map.of(HttpHeaders.AUTHORIZATION, jwtToken),
                        Map.of("role", role),
                        ControllerGenericResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public Boolean validateApplicationCode(UUID uuidExternalApp) {

        Boolean response;
        try {
            response = httpURLConnection.sendGET(
                    GUARDIAN_SERVICE_URL + "/application/" + uuidExternalApp.toString(),
                    Boolean.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;    }

    @Override
    public RegisterApplicationResponse registerApp(RegisterApplicationRequest request, String jwtToken) {

        RegisterApplicationResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/register-app",
                    Map.of(HttpHeaders.AUTHORIZATION, jwtToken),
                    request,
                    RegisterApplicationResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public CreateNewAccountResponse createAccount(CreateNewAccountRequest request) {

        CreateNewAccountResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/create-account",
                    request,
                    CreateNewAccountResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public ControllerGenericResponse createGroup(String jwtToken, GroupRequest request) {

        ControllerGenericResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/group",
                    request,
                    ControllerGenericResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public ControllerGenericResponse createGroupUserRelationship(String jwtToken, CreateGroupUserRequest request) {

        ControllerGenericResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/groupuser",
                    request,
                    ControllerGenericResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public RoleResponse createRole(RoleRequest request, String jwtToken) {

        RoleResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/role",
                    request,
                    RoleResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public ControllerGenericResponse createRoleGroupRelationship(String jwtToken, CreateRoleGroupRequest request) {

        ControllerGenericResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/rolegroup",
                    request,
                    ControllerGenericResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public MensagemResponse createBatchRoleGroup(String jwtToken, CreateBatchRoleGroupRequest request) {

        MensagemResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/batchrolegroup",
                    request,
                    MensagemResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public ControllerGenericResponse createRoleUserRelationship(String jwtToken, CreatePrivateRoleUserRequest request) {

        ControllerGenericResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/roleuser",
                    request,
                    ControllerGenericResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

    @Override
    public ValidateAccountResponse validateAccount(UUID externalAppUUID, ValidateAccountRequest request) {

        ValidateAccountResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                    GUARDIAN_SERVICE_URL + "/validate/account",
                    request,
                    ValidateAccountResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(GUARDIAN_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

}
