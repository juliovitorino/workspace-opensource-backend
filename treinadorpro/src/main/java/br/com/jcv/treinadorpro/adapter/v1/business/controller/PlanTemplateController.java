package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.plantemplate.FindAllPlanTemplateByStatus;
import br.com.jcv.treinadorpro.corebusiness.plantemplate.PlanTemplateResponse;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/plan")
public class PlanTemplateController {

    private final FindAllPlanTemplateByStatus findAllPlanTemplateByStatus;

    public PlanTemplateController(FindAllPlanTemplateByStatus findAllPlanTemplateByStatus) {
        this.findAllPlanTemplateByStatus = findAllPlanTemplateByStatus;
    }

    @GetMapping("/active")
    public ResponseEntity<ControllerGenericResponse<List<PlanTemplateResponse>>> findAllActivePlan() {
        return ResponseEntity.ok(findAllPlanTemplateByStatus.execute(UUID.randomUUID(), StatusEnum.A.toString()));
    }
}
