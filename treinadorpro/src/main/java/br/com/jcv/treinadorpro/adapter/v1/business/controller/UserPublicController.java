package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.restclient.guardian.request.RegisterResponse;
import br.com.jcv.restclient.guardian.request.ValidateSixCodeRequest;
import br.com.jcv.treinadorpro.corebusiness.users.LoginService;
import br.com.jcv.treinadorpro.corebusiness.users.RegisterNewPersonalTrainerService;
import br.com.jcv.treinadorpro.corebusiness.users.ValidateSixCodeService;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/public/user")
public class UserPublicController {

    private final LoginService loginService;
    private final RegisterNewPersonalTrainerService registerNewPersonalTrainerService;
    private final ValidateSixCodeService validateSixCodeService;

    public UserPublicController(LoginService loginService,
                                RegisterNewPersonalTrainerService registerNewPersonalTrainerService,
                                ValidateSixCodeService validateSixCodeService) {
        this.loginService = loginService;
        this.registerNewPersonalTrainerService = registerNewPersonalTrainerService;
        this.validateSixCodeService = validateSixCodeService;
    }

    @PostMapping("/login")
    public ResponseEntity<ControllerGenericResponse<String>> login(@RequestBody LoginRequest loginRequest,
                                                                   @RequestHeader("X-API-KEY") UUID apiKey) {
        loginRequest.setApplicationExternalUUID(apiKey);
        return ResponseEntity.ok(loginService.execute(UUID.randomUUID(), loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<ControllerGenericResponse<RegisterResponse>> registerPersonalTrainer(
            @RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(registerNewPersonalTrainerService.execute(UUID.randomUUID(), request));
    }

    @GetMapping("/trainer/validate/{externalid}/{requiredCode}")
    public ResponseEntity<ControllerGenericResponse<Boolean>> validateSixDigitCode(
            @RequestHeader("X-API-KEY") UUID apiKey,
            @PathVariable("externalid") UUID externalId,
            @PathVariable("requiredCode") String code
    ) {
        return ResponseEntity.ok(validateSixCodeService.execute(
                        UUID.randomUUID(),
                        ValidateSixCodeRequest.builder()
                                .externalAppUUID(apiKey)
                                .externalUserUUID(externalId)
                                .requiredCode(code)
                                .build()
                )
        );
    }


}
