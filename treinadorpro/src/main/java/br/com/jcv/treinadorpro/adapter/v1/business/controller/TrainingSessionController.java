package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindMostRecentTrainingSessionService;
import br.com.jcv.treinadorpro.corebusiness.usecases.SaveTrainingSessionService;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/training/session")
public class TrainingSessionController {

    private final SaveTrainingSessionService saveTrainingSessionService;
    private final FindMostRecentTrainingSessionService findMostRecentTrainingSessionService;

    public TrainingSessionController(SaveTrainingSessionService saveTrainingSessionService,
                                     FindMostRecentTrainingSessionService findMostRecentTrainingSessionService) {
        this.saveTrainingSessionService = saveTrainingSessionService;
        this.findMostRecentTrainingSessionService = findMostRecentTrainingSessionService;
    }

    @PostMapping
    public ResponseEntity<ControllerGenericResponse<Boolean>> saveTrainingSession(@RequestBody TrainingSessionRequest trainingSessionRequest){
        return ResponseEntity.ok(saveTrainingSessionService.execute(UUID.randomUUID(), trainingSessionRequest));

    }

    @GetMapping("/{externalId}")
    public ResponseEntity<ControllerGenericResponse<TrainingSessionRequest>> findMostRecentTrainingSessionService(@PathVariable("externalId") UUID contractExternalId){
        return ResponseEntity.ok(findMostRecentTrainingSessionService.execute(UUID.randomUUID(), contractExternalId));
    }
}
