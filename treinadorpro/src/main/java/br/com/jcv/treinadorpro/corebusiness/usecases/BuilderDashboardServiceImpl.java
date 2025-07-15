package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.StudentPaymentRepository;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.response.BuilderDashboardResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class BuilderDashboardServiceImpl implements BuilderDashboardService{

    private final ContractRepository contractRepository;
    private final GetLoggedUserService getLoggedUserService;
    private final StudentPaymentRepository studentPaymentRepository;
    private final TrainingPackRepository trainingPackRepository;

    public BuilderDashboardServiceImpl(ContractRepository contractRepository,
                                       GetLoggedUserService getLoggedUserService,
                                       StudentPaymentRepository studentPaymentRepository,
                                       TrainingPackRepository trainingPackRepository) {
        this.contractRepository = contractRepository;
        this.getLoggedUserService = getLoggedUserService;
        this.studentPaymentRepository = studentPaymentRepository;
        this.trainingPackRepository = trainingPackRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<BuilderDashboardResponse> execute(UUID processId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        BigDecimal overdueAmountContracts = studentPaymentRepository.sumOverduePayments(trainer.getId());
        BigDecimal totalAmountReceivedMonth = studentPaymentRepository.sumReceivedPaymentsCurrentMonth(trainer.getId());
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1449",
                "Dashboard Response has been executed successfully",
                BuilderDashboardResponse.builder()
                        .activeStudentContract(contractRepository.countOpenAndActiveContracts(SituationEnum.OPEN, StatusEnum.A, trainer.getId()))
                        .overdueAmountContracts(overdueAmountContracts == null ? BigDecimal.ZERO : overdueAmountContracts)
                        .totalTrainingPack(trainingPackRepository.countTrainingPack(getLoggedUserService.execute(processId).getId()))
                        .totalTodayWorkout(contractRepository.countTodayWorkout(SituationEnum.OPEN.name(), StatusEnum.A.name(), trainer.getId()))
                        .totalAmountReceivedMonth(totalAmountReceivedMonth == null ? BigDecimal.ZERO : totalAmountReceivedMonth)
                        .build()
        );
    }
}
