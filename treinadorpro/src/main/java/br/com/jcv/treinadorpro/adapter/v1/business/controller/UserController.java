package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.treinadorpro.corebusiness.users.LoginService;
import br.com.jcv.treinadorpro.corebusiness.users.RegisterNewPersonalTrainerService;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;

import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/user")
public class UserController {

    private final RegisterNewPersonalTrainerService registerNewPersonalTrainerService;
    private final LoginService loginService;

    public UserController(RegisterNewPersonalTrainerService registerNewPersonalTrainerService, LoginService loginService) {
        this.registerNewPersonalTrainerService = registerNewPersonalTrainerService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<ControllerGenericResponse<UserDTO>> registerPersonalTrainer(@RequestBody RegisterRequest userdto) {
        return ResponseEntity.ok(registerNewPersonalTrainerService.execute(UUID.randomUUID(), userdto));
    }

    @PostMapping("/login")
    public ResponseEntity<ControllerGenericResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.execute(UUID.randomUUID(), loginRequest));
    }
}
