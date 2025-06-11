package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.exercise.FindAllExerciseService;
import br.com.jcv.treinadorpro.corelayer.response.ExerciseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/exercise")
public class ExerciseController {

    private final FindAllExerciseService findAllExerciseService;

    public ExerciseController(FindAllExerciseService findAllExerciseService) {
        this.findAllExerciseService = findAllExerciseService;
    }

    @GetMapping
    public ResponseEntity<ControllerGenericResponse<List<ExerciseResponse>>> findAllExercises() {
        return ResponseEntity.ok(findAllExerciseService.execute(UUID.randomUUID(), Boolean.TRUE));
    }
}
