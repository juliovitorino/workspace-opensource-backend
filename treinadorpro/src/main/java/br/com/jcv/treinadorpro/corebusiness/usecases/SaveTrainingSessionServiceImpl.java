package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingExecutionSet;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSessionExercise;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutPlan;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserTrainingSessionRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutPlanRepository;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;
import br.com.jcv.treinadorpro.corelayer.request.UserWorkoutPlanRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.UserTrainingExecutionSetResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaveTrainingSessionServiceImpl implements SaveTrainingSessionService {

    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;
    private final UserWorkoutPlanRepository userWorkoutPlanRepository;
    private final UserTrainingSessionRepository userTrainingSessionRepository;

    public SaveTrainingSessionServiceImpl(GetLoggedUserService getLoggedUserService,
                                          ContractRepository contractRepository,
                                          UserWorkoutPlanRepository userWorkoutPlanRepository,
                                          UserTrainingSessionRepository userTrainingSessionRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
        this.userWorkoutPlanRepository = userWorkoutPlanRepository;
        this.userTrainingSessionRepository = userTrainingSessionRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, TrainingSessionRequest trainingSessionRequest) {

        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        Contract contract = contractRepository.findByExternalIdAndPersonalId(trainingSessionRequest.getContract().getExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Contract", HttpStatus.BAD_REQUEST, "MSG-1757"));

        List<UserWorkoutPlanRequest> executedExercises = trainingSessionRequest.getUserWorkoutPlanList().stream()
                .filter(e -> e.getUserExecutionSetList() != null && !e.getUserExecutionSetList().isEmpty())
                .collect(Collectors.toList());
        if(trainingSessionRequest.getProgressStatus().equalsIgnoreCase("BOOKING")) {
            executedExercises = trainingSessionRequest.getUserWorkoutPlanList();
        }

        UserTrainingSession userTrainingSessionInstance = getUserTrainingSessionInstance(trainingSessionRequest, contract, executedExercises);

        userTrainingSessionRepository.save(userTrainingSessionInstance);
        if(trainingSessionRequest.getBookingExternalId() != null){
            userTrainingSessionRepository.deleteUserTrainingSessionByExternalId(trainingSessionRequest.getBookingExternalId());
        }

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1739",
                "Your training session has been saved.",
                Boolean.TRUE
        );
    }

    private UserTrainingSession getUserTrainingSessionInstance(TrainingSessionRequest trainingSessionRequest,
                                                               Contract contract,
                                                               List<UserWorkoutPlanRequest> executedExercises) {
        UserTrainingSession userTrainingSession = new UserTrainingSession();
        userTrainingSession.setExternalId(UUID.randomUUID());
        userTrainingSession.setContract(contract);
        userTrainingSession.setBooking(trainingSessionRequest.getBooking());
        userTrainingSession.setStartAt(trainingSessionRequest.getStartAt());
        userTrainingSession.setFinishedAt(trainingSessionRequest.getFinishedAt() == null ? trainingSessionRequest.getStartAt() : trainingSessionRequest.getFinishedAt());
        userTrainingSession.setElapsedTime(calculateElapsedTime(trainingSessionRequest.getStartAt(), trainingSessionRequest.getFinishedAt()));
        userTrainingSession.setProgressStatus(trainingSessionRequest.getProgressStatus());
        userTrainingSession.setSyncStatus(trainingSessionRequest.getSyncStatus());
        userTrainingSession.setStatus(StatusEnum.A);
        userTrainingSession.setExercises(getExecutedExercises(executedExercises, userTrainingSession));
        return userTrainingSession;
    }

    private List<UserTrainingSessionExercise> getExecutedExercises(List<UserWorkoutPlanRequest> executedExercises,
                                                                   UserTrainingSession userTrainingSession) {
        return executedExercises.stream()
                .map( e-> this.getUserTrainingSessionExerciseInstance(e, userTrainingSession) )
                .collect(Collectors.toList());
    }

    private UserTrainingSessionExercise getUserTrainingSessionExerciseInstance(UserWorkoutPlanRequest exercise,
                                                                               UserTrainingSession userTrainingSession) {

        UserWorkoutPlan userWorkoutPlan = userWorkoutPlanRepository.findByExternalId(exercise.getExternalId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Exercise", HttpStatus.BAD_REQUEST, "MSG-1822"));

        UserTrainingSessionExercise userTrainingSessionExercise = new UserTrainingSessionExercise();
        userTrainingSessionExercise.setExternalId(UUID.randomUUID());
        userTrainingSessionExercise.setUserTrainingSession(userTrainingSession);
        userTrainingSessionExercise.setStatus(StatusEnum.A);
        userTrainingSessionExercise.setUserWorkoutPlan(userWorkoutPlan);
        userTrainingSessionExercise.setExecutionSets(getExecutionSetList(exercise.getUserExecutionSetList(), userTrainingSessionExercise));
        return userTrainingSessionExercise;
    }

    private List<UserTrainingExecutionSet> getExecutionSetList(List<UserTrainingExecutionSetResponse> userExecutionSetList,
                                                               UserTrainingSessionExercise userTrainingSessionExercise) {
        if(userExecutionSetList == null) return Collections.emptyList();
        return userExecutionSetList.stream()
                .map(e-> getExecutionSetInstance(e,userTrainingSessionExercise))
                .collect(Collectors.toList());
    }

    private UserTrainingExecutionSet getExecutionSetInstance(UserTrainingExecutionSetResponse executionSet,
                                                             UserTrainingSessionExercise userTrainingSessionExercise) {
        return UserTrainingExecutionSet.builder()
                .externalId(UUID.randomUUID())
                .userTrainingSessionExercise(userTrainingSessionExercise)
                .reps(executionSet.getReps())
                .startedAt(executionSet.getStartedAt())
                .finishedAt(executionSet.getFinishedAt() == null ? executionSet.getStartedAt() : executionSet.getFinishedAt())
                .setNumber(executionSet.getSetNumber())
                .weight(executionSet.getWeight())
                .weightUnit("kg")
                .elapsedTime(calculateElapsedTime(executionSet.getStartedAt(), executionSet.getFinishedAt()))
                .status(StatusEnum.A)
                .build();
    }

    private Integer calculateElapsedTime(LocalDateTime startedAt, LocalDateTime finishedAt){
        if(finishedAt == null) return 0;
        long seconds = Duration.between(startedAt, finishedAt).getSeconds();
        return (int) seconds;
    }

}
