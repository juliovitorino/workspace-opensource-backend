package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.treinadorpro.corebusiness.users.AddNewCustomPersonalTrainerProgramItemService;
import br.com.jcv.treinadorpro.corebusiness.users.CreatePersonalTrainerProgramFromTemplateService;
import br.com.jcv.treinadorpro.corebusiness.users.CreateStudentAccountService;
import br.com.jcv.treinadorpro.corebusiness.users.EditPersonalTrainerProgramService;
import br.com.jcv.treinadorpro.corebusiness.users.EditStudentProfileService;
import br.com.jcv.treinadorpro.corebusiness.users.LoginService;
import br.com.jcv.treinadorpro.corebusiness.users.RegisterNewPersonalTrainerService;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;

import br.com.jcv.treinadorpro.corelayer.request.CreatePersonalTrainerProgramFromTemplateRequest;
import br.com.jcv.treinadorpro.corelayer.request.EditPersonalTrainerProgramRequest;
import br.com.jcv.treinadorpro.corelayer.request.PersonalTrainerProgramRequest;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.corelayer.request.StudentProfileRequest;
import org.springframework.http.ResponseEntity;
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
    private final CreateStudentAccountService createStudentAccountService;
    private final LoginService loginService;
    private final EditStudentProfileService editStudentProfileService;
    private final CreatePersonalTrainerProgramFromTemplateService createPersonalTrainerProgramFromTemplateService;
    private final EditPersonalTrainerProgramService editPersonalTrainerProgramService;
    private final AddNewCustomPersonalTrainerProgramItemService addNewCustomPersonalTrainerProgramItemService;

    public UserController(RegisterNewPersonalTrainerService registerNewPersonalTrainerService,
                          CreateStudentAccountService createStudentAccountService,
                          LoginService loginService, EditStudentProfileService editStudentProfileService,
                          CreatePersonalTrainerProgramFromTemplateService createPersonalTrainerProgramFromTemplateService,
                          EditPersonalTrainerProgramService editPersonalTrainerProgramService,
                          AddNewCustomPersonalTrainerProgramItemService addNewCustomPersonalTrainerProgramItemService) {
        this.registerNewPersonalTrainerService = registerNewPersonalTrainerService;
        this.createStudentAccountService = createStudentAccountService;
        this.loginService = loginService;
        this.editStudentProfileService = editStudentProfileService;
        this.createPersonalTrainerProgramFromTemplateService = createPersonalTrainerProgramFromTemplateService;
        this.editPersonalTrainerProgramService = editPersonalTrainerProgramService;
        this.addNewCustomPersonalTrainerProgramItemService = addNewCustomPersonalTrainerProgramItemService;
    }

    @PostMapping("/register")
    public ResponseEntity<ControllerGenericResponse<UserDTO>> registerPersonalTrainer(@RequestBody @Valid RegisterRequest userdto) {
        return ResponseEntity.ok(registerNewPersonalTrainerService.execute(UUID.randomUUID(), userdto));
    }

    @PostMapping("/register/student")
    public ResponseEntity<ControllerGenericResponse<String>> createStudentAccount(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(createStudentAccountService.execute(UUID.randomUUID(), request));

    }

    @PostMapping("/login")
    public ResponseEntity<ControllerGenericResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.execute(UUID.randomUUID(), loginRequest));
    }

    @PutMapping("/student/{uuid}")
    public ResponseEntity<ControllerGenericResponse<Boolean>> editStudentProfile(@PathVariable("uuid") UUID uuidId,
                                                                                 @RequestBody StudentProfileRequest request) {
        request.setUuidId(uuidId);
        return ResponseEntity.ok(editStudentProfileService.execute(UUID.randomUUID(),request));
    }

    @PostMapping("personal/program/item")
    public ResponseEntity<ControllerGenericResponse<Boolean>> addNewCustomPersonalTrainerProgramItemService(
            @RequestBody PersonalTrainerProgramRequest request) {
        return ResponseEntity.ok(addNewCustomPersonalTrainerProgramItemService.execute(UUID.randomUUID(), request));
    }

    @PostMapping("personal/program")
    public ResponseEntity<ControllerGenericResponse<Boolean>> createPersonalTrainerProgramFromTemplate(
            @RequestBody CreatePersonalTrainerProgramFromTemplateRequest request) {
        return ResponseEntity.ok(createPersonalTrainerProgramFromTemplateService.execute(UUID.randomUUID(), request));
    }

    @PutMapping("personal/program")
    public ResponseEntity<ControllerGenericResponse<Boolean>> editPersonalTrainerProgram(@RequestBody EditPersonalTrainerProgramRequest request){
        return ResponseEntity.ok(editPersonalTrainerProgramService.execute(UUID.randomUUID(), request));
    }
}
