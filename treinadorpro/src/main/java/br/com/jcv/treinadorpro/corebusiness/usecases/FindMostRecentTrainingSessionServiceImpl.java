package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserTrainingSessionRepository;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class FindMostRecentTrainingSessionServiceImpl implements FindMostRecentTrainingSessionService {

    private final UserTrainingSessionRepository userTrainingSessionRepository;
    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;

    public FindMostRecentTrainingSessionServiceImpl(UserTrainingSessionRepository userTrainingSessionRepository,
                                                    GetLoggedUserService getLoggedUserService,
                                                    ContractRepository contractRepository) {
        this.userTrainingSessionRepository = userTrainingSessionRepository;
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
    }

    @Override
    public ControllerGenericResponse<TrainingSessionRequest> execute(UUID processId, UUID contractExternalId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        Contract contract = contractRepository.findByExternalIdAndPersonalId(contractExternalId, trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid contract", HttpStatus.BAD_REQUEST, "MSG-1227"));

        UserTrainingSession userTrainingSession = userTrainingSessionRepository.findTopByContractExternalIdAndProgressStatusOrderByIdDesc(contract.getExternalId(), "FINISHED")
                .orElse(null);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1150",
                "Latest training session has been retrieved successfully"
                , MapperServiceHelper.toResponse(userTrainingSession)
        );
    }
}
