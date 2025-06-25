package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.restclient.guardian.request.RegisterResponse;
import br.com.jcv.restclient.guardian.request.ValidateSixCodeRequest;
import br.com.jcv.treinadorpro.corebusiness.users.EditStudentProfileService;
import br.com.jcv.treinadorpro.corebusiness.users.FindPersonalTrainerService;
import br.com.jcv.treinadorpro.corebusiness.users.LoginService;
import br.com.jcv.treinadorpro.corebusiness.users.RegisterNewPersonalTrainerService;
import br.com.jcv.treinadorpro.corebusiness.users.ValidateSixCodeService;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.corelayer.request.StudentProfileRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/user")
public class UserController {

    private final RegisterNewPersonalTrainerService registerNewPersonalTrainerService;
    private final LoginService loginService;
    private final EditStudentProfileService editStudentProfileService;
    private final FindPersonalTrainerService findPersonalTrainerService;
    private final ValidateSixCodeService validateSixCodeService;

    public UserController(RegisterNewPersonalTrainerService registerNewPersonalTrainerService,
                          LoginService loginService,
                          EditStudentProfileService editStudentProfileService,
                          FindPersonalTrainerService findPersonalTrainerService,
                          ValidateSixCodeService validateSixCodeService) {
        this.registerNewPersonalTrainerService = registerNewPersonalTrainerService;
        this.loginService = loginService;
        this.editStudentProfileService = editStudentProfileService;
        this.findPersonalTrainerService = findPersonalTrainerService;
        this.validateSixCodeService = validateSixCodeService;
    }

    @PostMapping("/register")
    public ResponseEntity<ControllerGenericResponse<RegisterResponse>> registerPersonalTrainer(@RequestBody @Valid RegisterRequest userdto) {
        return ResponseEntity.ok(registerNewPersonalTrainerService.execute(UUID.randomUUID(), userdto));
    }

    @PostMapping("/login")
    public ResponseEntity<ControllerGenericResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.execute(UUID.randomUUID(), loginRequest));
    }

    @PutMapping("/student/{uuid}")
    public ResponseEntity<ControllerGenericResponse<Boolean>> editStudentProfile(@PathVariable("uuid") UUID uuidId,
                                                                                 @RequestBody StudentProfileRequest request) {
        request.setUuidId(uuidId);
        return ResponseEntity.ok(editStudentProfileService.execute(UUID.randomUUID(), request));
    }

    @GetMapping("/trainer/{uuid}")
    public ResponseEntity<ControllerGenericResponse<PersonalTrainerResponse>> findPersonalTrainer(@PathVariable("uuid") UUID uuidId) {
        return ResponseEntity.ok(findPersonalTrainerService.execute(UUID.randomUUID(), uuidId));
    }

    @GetMapping("/trainer/validate/{apikey}/{externalid}/{requiredCode}")
    public ResponseEntity<ControllerGenericResponse<Boolean>> validateSixDigitCode(
            @PathVariable("apikey") UUID apiKey,
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
