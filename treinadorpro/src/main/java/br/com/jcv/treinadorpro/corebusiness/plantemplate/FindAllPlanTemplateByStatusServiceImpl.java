package br.com.jcv.treinadorpro.corebusiness.plantemplate;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.PlanTemplate;
import br.com.jcv.treinadorpro.corelayer.repository.PlanTemplateRepository;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FindAllPlanTemplateByStatusServiceImpl implements FindAllPlanTemplateByStatus{

    private final PlanTemplateRepository planTemplateRepository;

    public FindAllPlanTemplateByStatusServiceImpl(PlanTemplateRepository planTemplateRepository) {
        this.planTemplateRepository = planTemplateRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<List<PlanTemplateResponse>> execute(UUID processId, String status) {
        log.info("({}) FindAllPlanTemplateByStatus.execute has been started", processId);

        List<PlanTemplate> allByStatus = planTemplateRepository.findAllByStatus(status);

        log.info("({}) FindAllPlanTemplateByStatus.execute has been finished", processId);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-0906",
                "All Plan Template were retrieved successfully",
                allByStatus.stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
