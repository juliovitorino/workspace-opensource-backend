package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllStudentsFromTrainerService;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/userpacktraining")
public class UserPackTrainingController {

    private final FindAllStudentsFromTrainerService findAllStudentsFromTrainerService;

    public UserPackTrainingController(FindAllStudentsFromTrainerService findAllStudentsFromTrainerService) {
        this.findAllStudentsFromTrainerService = findAllStudentsFromTrainerService;
    }

    @GetMapping("{externalId}")
    public ResponseEntity<ControllerGenericResponse<List<StudentsFromTrainerResponse>>> findAllStudentsFromTrainerService(@PathVariable("externalId")UUID externalId){
        return ResponseEntity.ok(findAllStudentsFromTrainerService.execute(UUID.randomUUID(), externalId));
    }

}

