package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.trainingpack.FindAllTrainingPackService;
import br.com.jcv.treinadorpro.corebusiness.usecases.AddTrainingPackService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllTrainingPackFromTrainerService;
import br.com.jcv.treinadorpro.corelayer.request.AddTrainingPackRequest;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.PageResultRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.PageResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/trainingpack")
public class TrainingPackController {

    private final FindAllTrainingPackService findAllTrainingPackService;
    private final FindAllTrainingPackFromTrainerService findAllTrainingPackFromTrainerService;
    private final AddTrainingPackService addTrainingPackService;

    public TrainingPackController(FindAllTrainingPackService findAllTrainingPackService,
                                  FindAllTrainingPackFromTrainerService findAllTrainingPackFromTrainerService,
                                  AddTrainingPackService addTrainingPackService) {
        this.findAllTrainingPackService = findAllTrainingPackService;
        this.findAllTrainingPackFromTrainerService = findAllTrainingPackFromTrainerService;
        this.addTrainingPackService = addTrainingPackService;
    }

    @PostMapping
    @Deprecated
    private ResponseEntity<ControllerGenericResponse<Boolean>> addTrainingPack(@RequestBody @Valid AddTrainingPackRequest request) {
        return ResponseEntity.ok(addTrainingPackService.execute(UUID.randomUUID(), request));
    }

    @GetMapping()
    private ResponseEntity<ControllerGenericResponse<PageResultResponse<TrainingPackResponse>>> findAllTrainingPack(
            @RequestParam("externalId") UUID personalTrainerExternalId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return ResponseEntity.ok(
                findAllTrainingPackService.execute(
                        UUID.randomUUID(),
                        PageResultRequest.<UUID>builder()
                                .page(page)
                                .size(size)
                                .request(personalTrainerExternalId)
                                .build()
                )
        );
    }

    @GetMapping("{externalId}")
    private ResponseEntity<ControllerGenericResponse<List<TrainingPackResponse>>> findAllTrainingPackFromTrainer(
            @PathVariable("externalId") UUID personalTrainerExternalId) {
        return ResponseEntity.ok(findAllTrainingPackFromTrainerService.execute(UUID.randomUUID(), personalTrainerExternalId));
    }
}
