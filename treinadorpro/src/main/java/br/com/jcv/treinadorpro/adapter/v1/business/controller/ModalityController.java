package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.goal.FindAllGoalService;
import br.com.jcv.treinadorpro.corebusiness.modality.FindAllModalityService;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/modality")
public class ModalityController {

    private final FindAllModalityService findAllModalityService;

    public ModalityController(FindAllModalityService findAllModalityService) {
        this.findAllModalityService = findAllModalityService;
    }

    @GetMapping
    public ResponseEntity<ControllerGenericResponse<List<ModalityResponse>>> findAllModality() {
        return ResponseEntity.ok(findAllModalityService.execute(UUID.randomUUID(), Boolean.TRUE));
    }
}
