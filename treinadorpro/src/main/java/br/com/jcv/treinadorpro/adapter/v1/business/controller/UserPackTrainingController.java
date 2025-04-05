package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.CreateUserPackTrainingService;
import br.com.jcv.treinadorpro.corebusiness.users.LoginService;
import br.com.jcv.treinadorpro.corebusiness.users.RegisterNewPersonalTrainerService;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.corelayer.request.UserPackTrainingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/userpacktraining")
public class UserPackTrainingController {


    private final CreateUserPackTrainingService createUserPackTrainingService;

    public UserPackTrainingController(CreateUserPackTrainingService createUserPackTrainingService) {
        this.createUserPackTrainingService = createUserPackTrainingService;
    }

    @PostMapping("/add")
    public ResponseEntity<ControllerGenericResponse<UserPackTrainingDTO>> addUserPackTrainig(@RequestBody UserPackTrainingRequest request) {
        return ResponseEntity.ok(createUserPackTrainingService.execute(UUID.randomUUID(), request));
    }

}
