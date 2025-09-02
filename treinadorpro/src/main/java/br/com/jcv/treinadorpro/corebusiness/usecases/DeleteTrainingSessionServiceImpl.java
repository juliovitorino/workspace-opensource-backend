package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserTrainingSessionRepository;
import br.com.jcv.treinadorpro.corelayer.request.DeleteTrainingSessionRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class DeleteTrainingSessionServiceImpl implements DeleteTrainingSessionService {

    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;
    private final UserTrainingSessionRepository userTrainingSessionRepository;

    public DeleteTrainingSessionServiceImpl(GetLoggedUserService getLoggedUserService,
                                            ContractRepository contractRepository,
                                            UserTrainingSessionRepository userTrainingSessionRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
        this.userTrainingSessionRepository = userTrainingSessionRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, DeleteTrainingSessionRequest request) {

        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        Contract contract = contractRepository.findByExternalIdAndPersonalId(request.getContractExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Contract", HttpStatus.BAD_REQUEST, "MSG-1620"));

        UserTrainingSession userTrainingSession = userTrainingSessionRepository.findByExternalIdAndContractId(
                        request.getTrainingSessionExternalId(),
                        contract.getId()
                )
                .orElseThrow(() -> new CommoditieBaseException("Invalid Training Session", HttpStatus.NOT_FOUND, "MSG-1641"));

        if(!userTrainingSession.getProgressStatus().equals("BOOKING")){
            throw new CommoditieBaseException("Training Session is on invalid status for delete (Only BOOKING)", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1644");
        }

        userTrainingSessionRepository.deleteById(userTrainingSession.getId());

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1629",
                "Your booked training session has been removed from calendar",
                Boolean.TRUE
        );
    }
}
