package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserTrainingSessionRepository;
import br.com.jcv.treinadorpro.corelayer.request.FindAllTrainingSessionCalendarRequest;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FindAllTrainingSessionCalendarServiceImpl implements FindAllTrainingSessionCalendarService {
    private final UserTrainingSessionRepository userTrainingSessionRepository;
    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;

    public FindAllTrainingSessionCalendarServiceImpl(UserTrainingSessionRepository userTrainingSessionRepository,
                                                     GetLoggedUserService getLoggedUserService,
                                                     ContractRepository contractRepository) {
        this.userTrainingSessionRepository = userTrainingSessionRepository;
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
    }

    @Override
    public ControllerGenericResponse<List<TrainingSessionRequest>> execute(UUID processId,
                                                                           FindAllTrainingSessionCalendarRequest request) {


        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        Contract contract = contractRepository.findByExternalIdAndPersonalId(request.getContractExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid contract", HttpStatus.BAD_REQUEST, "MSG-1113"));

        List<UserTrainingSession> trainingSessionCalendar =
                userTrainingSessionRepository.findTrainingSessionByContractExternalIdAndStartDateAndEndDate(
                        contract.getExternalId(),
                        request.getStartDate(),
                        request.getEndDate()
                );
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1118",
                "All Training Session Calendar has been retrieved",
                trainingSessionCalendar.stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
