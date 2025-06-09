package br.com.jcv.treinadorpro.adapter.v1.business.controller;

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

    private final FindAllGoalService findAllWorkGroupService;

    public GoalController(FindAllGoalService findAllWorkGroupService) {
        this.findAllWorkGroupService = findAllWorkGroupService;
    }

    @GetMapping
    public ResponseEntity<List<GoalResponse>> findAllProgram() {
        return ResponseEntity.ok(findAllWorkGroupService.execute(UUID.randomUUID(), Boolean.TRUE));
    }
}
