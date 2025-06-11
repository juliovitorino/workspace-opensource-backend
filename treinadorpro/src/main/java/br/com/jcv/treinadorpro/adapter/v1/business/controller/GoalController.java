package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.goal.FindAllGoalService;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/goal")
public class GoalController {

    private final FindAllGoalService findAllGoalService;

    public GoalController(FindAllGoalService findAllGoalService) {
        this.findAllGoalService = findAllGoalService;
    }

    @GetMapping
    public ResponseEntity<ControllerGenericResponse<List<GoalResponse>>> findAllGoal() {
        return ResponseEntity.ok(findAllGoalService.execute(UUID.randomUUID(), Boolean.TRUE));
    }
}
