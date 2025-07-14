package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FindAllContractTodayWorkoutServiceImpl implements FindAllContractTodayWorkoutService{

    private final ContractRepository contractRepository;
    private final GetLoggedUserService getLoggedUserService;

    public FindAllContractTodayWorkoutServiceImpl(ContractRepository contractRepository, GetLoggedUserService getLoggedUserService) {
        this.contractRepository = contractRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    public ControllerGenericResponse<List<ContractResponse>> execute(UUID processId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        List<Contract> allContractTodayWorkout = contractRepository.findAllContractTodayWorkout(
                SituationEnum.OPEN.name(),
                StatusEnum.A.name(),
                trainer.getId()
        );

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1503",
                "Today contracts have been retrieved successfully",
                allContractTodayWorkout.stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
