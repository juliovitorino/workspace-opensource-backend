package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.response.BuilderDashboardResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
public class BuilderDashboardServiceImpl implements BuilderDashboardService{

    private final CounterStudentContractsService counterStudentContractsService;

    public BuilderDashboardServiceImpl(CounterStudentContractsService counterStudentContractsService) {
        this.counterStudentContractsService = counterStudentContractsService;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<BuilderDashboardResponse> execute(UUID processId) {
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1449",
                "Dashboard Response has been executed successfully",
                BuilderDashboardResponse.builder()
                        .activeStudentContract(counterStudentContractsService.execute(processId, SituationEnum.OPEN))
                        .build()
        );
    }
}
