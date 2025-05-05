package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.CreateUserPackTrainingService;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.FindAllMyStudentsService;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.request.UserPackTrainingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/userpacktraining")
public class UserPackTrainingController {


    private final CreateUserPackTrainingService createUserPackTrainingService;
    private final FindAllMyStudentsService findAllMyStudentsService;

    public UserPackTrainingController(CreateUserPackTrainingService createUserPackTrainingService, FindAllMyStudentsService findAllMyStudentsService) {
        this.createUserPackTrainingService = createUserPackTrainingService;
        this.findAllMyStudentsService = findAllMyStudentsService;
    }

    @PostMapping("/add")
    public ResponseEntity<ControllerGenericResponse<Boolean>> createUserPackTrainig(@RequestBody UserPackTrainingRequest request) {
        return ResponseEntity.ok(createUserPackTrainingService.execute(UUID.randomUUID(), request));
    }

    @GetMapping("/list/{personalUserId}")
    public ResponseEntity<ControllerGenericResponse<List<UserPackTrainingDTO>>> findAllMyStudents(@PathVariable("personalUserId") Long personalUserId) {
        return ResponseEntity.ok(findAllMyStudentsService.execute(UUID.randomUUID(),personalUserId));
    }

}
