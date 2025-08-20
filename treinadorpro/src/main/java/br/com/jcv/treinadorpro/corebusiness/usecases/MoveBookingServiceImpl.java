package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserTrainingSessionRepository;
import br.com.jcv.treinadorpro.corelayer.request.MoveBookingRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
@Slf4j
public class MoveBookingServiceImpl implements MoveBookingService {

    private final GetLoggedUserService getLoggedUserService;
    private final UserTrainingSessionRepository userTrainingSessionRepository;
    private final ContractRepository contractRepository;

    public MoveBookingServiceImpl(GetLoggedUserService getLoggedUserService,
                                  UserTrainingSessionRepository userTrainingSessionRepository,
                                  ContractRepository contractRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.userTrainingSessionRepository = userTrainingSessionRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, MoveBookingRequest request) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        Contract contract = contractRepository.findByExternalIdAndPersonalId(request.getContractExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Contract", HttpStatus.BAD_REQUEST, "MSG-0857"));

        UserTrainingSession userTrainingSession = userTrainingSessionRepository.findByExternalIdAndContractId(request.getTrainingSessionExternalId(), contract.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Training Session", HttpStatus.BAD_REQUEST, "MSG-0904"));

        userTrainingSession.setBooking(request.getNewBookingDate().atTime(LocalTime.MIN));
        userTrainingSessionRepository.save(userTrainingSession);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1008",
                "Your booking has been changed",
                Boolean.TRUE
        );

    }
}
