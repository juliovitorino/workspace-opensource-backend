package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.trainingpack.CreateTrainingPackService;
import br.com.jcv.treinadorpro.corebusiness.trainingpack.FindAllTrainingPackService;
import br.com.jcv.treinadorpro.corelayer.request.CreateTrainingPackRequest;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/trainingpack")
public class TrainingPackController {

    private final CreateTrainingPackService createTrainingPackService;
    private final FindAllTrainingPackService  findAllTrainingPackService;

    public TrainingPackController(CreateTrainingPackService createTrainingPackService,
                                  FindAllTrainingPackService findAllTrainingPackService) {
        this.createTrainingPackService = createTrainingPackService;
        this.findAllTrainingPackService = findAllTrainingPackService;
    }

    @PostMapping
    private ResponseEntity<ControllerGenericResponse<Boolean>> createTrainingPack(@RequestBody @Valid CreateTrainingPackRequest request) {
        return ResponseEntity.ok(createTrainingPackService.execute(UUID.randomUUID(), request));
    }

    @GetMapping("{uuid}")
    private ResponseEntity<ControllerGenericResponse<List<TrainingPackResponse>>> findAllTrainingPack(@PathVariable("uuid") UUID personalExternalUUID) {
        return ResponseEntity.ok(findAllTrainingPackService.execute(UUID.randomUUID(), personalExternalUUID));
    }
}
