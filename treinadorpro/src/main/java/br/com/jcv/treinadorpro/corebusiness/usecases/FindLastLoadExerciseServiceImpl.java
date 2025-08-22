package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserTrainingExecutionSetRepository;
import br.com.jcv.treinadorpro.corelayer.request.FindLastLoadExerciseRequest;
import br.com.jcv.treinadorpro.corelayer.response.FindLastLoadExerciseResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Slf4j
public class FindLastLoadExerciseServiceImpl implements FindLastLoadExerciseService {

    private final GetLoggedUserService getLoggedUserService;
    private final UserTrainingExecutionSetRepository userTrainingExecutionSetRepository;
    private final ContractRepository contractRepository;


    public FindLastLoadExerciseServiceImpl(GetLoggedUserService getLoggedUserService,
                                           UserTrainingExecutionSetRepository userTrainingExecutionSetRepository,
                                           ContractRepository contractRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.userTrainingExecutionSetRepository = userTrainingExecutionSetRepository;
        this.contractRepository = contractRepository;
    }


    @Override
    public ControllerGenericResponse<FindLastLoadExerciseResponse> execute(UUID processId, FindLastLoadExerciseRequest request) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        contractRepository.findByExternalIdAndPersonalId(request.getContractExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Contract", HttpStatus.BAD_REQUEST, "MSG-1147"));

        FindLastLoadExerciseResponse response = FindLastLoadExerciseResponse.builder()
                .setNumber(0L)
                .reps(0L)
                .weight(0L)
                .build();

        Map<Boolean, Consumer<FindLastLoadExerciseRequest>> mapStrategy = getStrategyMap(request, response);

        mapStrategy.get(request.getCustomExercise() == null).accept(request);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1104",
                "Last load from exercise has been retrieved",
                response
        );
    }

    private Map<Boolean, Consumer<FindLastLoadExerciseRequest>> getStrategyMap(FindLastLoadExerciseRequest request, FindLastLoadExerciseResponse response) {
        Map<Boolean, Consumer<FindLastLoadExerciseRequest>> mapStrategy = new HashMap<>();

        mapStrategy.put(Boolean.TRUE, (r) -> userTrainingExecutionSetRepository.findLastLoadByExerciseExternalId(r.getExerciseExternalId())
                .ifPresent(n ->
                        {
                            response.setSetNumber(n.getSetNumber());
                            response.setReps(n.getReps());
                            response.setWeight(n.getWeight());
                        }
                )
        );
        mapStrategy.put(Boolean.FALSE, (r) -> userTrainingExecutionSetRepository.findLastLoadByCustomExercise
                        (
                                request.getContractExternalId(),
                                request.getCustomExercise().trim()
                        )
                        .ifPresent(n ->
                                {
                                    response.setSetNumber(n.getSetNumber());
                                    response.setReps(n.getReps());
                                    response.setWeight(n.getWeight());
                                }
                        )
        );
        return mapStrategy;
    }
}
