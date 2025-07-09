package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.restclient.guardian.request.RegisterResponse;
import br.com.jcv.restclient.guardian.request.ValidateSixCodeRequest;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindTrainerAvailableTimeService;
import br.com.jcv.treinadorpro.corebusiness.users.EditStudentProfileService;
import br.com.jcv.treinadorpro.corebusiness.users.FindPersonalTrainerService;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corebusiness.users.LoginService;
import br.com.jcv.treinadorpro.corebusiness.users.RegisterNewPersonalTrainerService;
import br.com.jcv.treinadorpro.corebusiness.users.ValidateSixCodeService;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.corelayer.request.StudentProfileRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.TrainerAvailableTimeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/user")
public class UserController {

    private final EditStudentProfileService editStudentProfileService;
    private final FindPersonalTrainerService findPersonalTrainerService;
    private final GetLoggedUserService getLoggedUserService;
    private final FindTrainerAvailableTimeService findTrainerAvailableTimeService;

    public UserController(RegisterNewPersonalTrainerService registerNewPersonalTrainerService,
                          EditStudentProfileService editStudentProfileService,
                          FindPersonalTrainerService findPersonalTrainerService,
                          ValidateSixCodeService validateSixCodeService,
                          GetLoggedUserService getLoggedUserService,
                          FindTrainerAvailableTimeService findTrainerAvailableTimeService) {
        this.editStudentProfileService = editStudentProfileService;
        this.findPersonalTrainerService = findPersonalTrainerService;
        this.getLoggedUserService = getLoggedUserService;
        this.findTrainerAvailableTimeService = findTrainerAvailableTimeService;
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

    @GetMapping("/trainer/logged")
    public ResponseEntity<ControllerGenericResponse<PersonalTrainerResponse>> getLoggedUser() {
        return ResponseEntity.ok(getLoggedUserService.execute(UUID.randomUUID()));
    }

    @GetMapping("/trainer/availabletimes")
    public ResponseEntity<ControllerGenericResponse<TrainerAvailableTimeResponse>> findTrainerAvailableTime() {
        return ResponseEntity.ok(findTrainerAvailableTimeService.execute(UUID.randomUUID()));
    }

}
