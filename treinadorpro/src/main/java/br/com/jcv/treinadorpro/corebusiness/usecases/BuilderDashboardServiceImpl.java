package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.response.BuilderDashboardResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
public class BuilderDashboardServiceImpl implements BuilderDashboardService{

    private final CounterStudentContractsService counterStudentContractsService;
    private final OverdueAmountContractService overdueAmountContractService;
    private final CounterTrainingPackService counterTrainingPackService;
    private final ContractRepository contractRepository;
    private final GetLoggedUserService getLoggedUserService;

    public BuilderDashboardServiceImpl(CounterStudentContractsService counterStudentContractsService,
                                       OverdueAmountContractService overdueAmountContractService,
                                       CounterTrainingPackService counterTrainingPackService,
                                       ContractRepository contractRepository,
                                       GetLoggedUserService getLoggedUserService) {
        this.counterStudentContractsService = counterStudentContractsService;
        this.overdueAmountContractService = overdueAmountContractService;
        this.counterTrainingPackService = counterTrainingPackService;
        this.contractRepository = contractRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<BuilderDashboardResponse> execute(UUID processId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1449",
                "Dashboard Response has been executed successfully",
                BuilderDashboardResponse.builder()
                        .activeStudentContract(counterStudentContractsService.execute(processId, SituationEnum.OPEN))
                        .overdueAmountContracts(overdueAmountContractService.execute(processId))
                        .totalTrainingPack(counterTrainingPackService.execute(processId))
                        .totalTodayWorkout(contractRepository.countTodayWorkout(SituationEnum.OPEN.name(), StatusEnum.A.name(), trainer.getId()))
                        .build()
        );
    }
}
