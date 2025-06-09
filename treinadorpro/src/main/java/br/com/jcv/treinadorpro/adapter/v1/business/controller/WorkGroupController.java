package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.treinadorpro.corebusiness.goal.FindAllGoalService;
import br.com.jcv.treinadorpro.corebusiness.workgroup.FindAllWorkGroupService;
import br.com.jcv.treinadorpro.corelayer.response.WorkGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/wg")
public class WorkGroupController {

    private final FindAllWorkGroupService findAllWorkGroupService;

    public WorkGroupController(FindAllWorkGroupService findAllWorkGroupService) {
        this.findAllWorkGroupService = findAllWorkGroupService;
    }

    @GetMapping
    public ResponseEntity<List<WorkGroupResponse>> findAllProgram() {
        return ResponseEntity.ok(findAllWorkGroupService.execute(UUID.randomUUID(), Boolean.TRUE));
    }
}
