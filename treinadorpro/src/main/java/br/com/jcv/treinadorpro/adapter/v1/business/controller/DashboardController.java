package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.usecases.BuilderDashboardService;
import br.com.jcv.treinadorpro.corelayer.response.BuilderDashboardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/dashboard")
public class DashboardController {

    private final BuilderDashboardService builderDashboardService;

    public DashboardController(BuilderDashboardService builderDashboardService) {
        this.builderDashboardService = builderDashboardService;
    }

    @GetMapping("/status")
    public ResponseEntity<ControllerGenericResponse<BuilderDashboardResponse>> dashboardStatus(){
        return ResponseEntity.ok(builderDashboardService.execute(UUID.randomUUID()));

    }

}
