package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.Exercise;
import br.com.jcv.treinadorpro.corelayer.model.Goal;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.Program;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutPlan;
import br.com.jcv.treinadorpro.corelayer.model.WorkGroup;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ExerciseRepository;
import br.com.jcv.treinadorpro.corelayer.repository.GoalRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ProgramRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutPlanRepository;
import br.com.jcv.treinadorpro.corelayer.repository.WorkGroupRepository;
import br.com.jcv.treinadorpro.corelayer.request.UserDataSheetPlanRequest;
import br.com.jcv.treinadorpro.corelayer.request.UserWorkoutPlanRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SaveUserWorkoutDataSheetPlanServiceImpl implements SaveUserWorkoutDataSheetPlanService {

    private final ContractRepository contractRepository;
    private final ModalityRepository modalityRepository;
    private final GoalRepository goalRepository;
    private final ProgramRepository programRepository;
    private final WorkGroupRepository workGroupRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserWorkoutPlanRepository userWorkoutPlanRepository;
    private final GetLoggedUserService getLoggedUserService;

    public SaveUserWorkoutDataSheetPlanServiceImpl(ContractRepository contractRepository,
                                                   ModalityRepository modalityRepository,
                                                   GoalRepository goalRepository,
                                                   ProgramRepository programRepository,
                                                   WorkGroupRepository workGroupRepository,
                                                   ExerciseRepository exerciseRepository,
                                                   UserWorkoutPlanRepository userWorkoutPlanRepository,
                                                   GetLoggedUserService getLoggedUserService) {
        this.contractRepository = contractRepository;
        this.modalityRepository = modalityRepository;
        this.goalRepository = goalRepository;
        this.programRepository = programRepository;
        this.workGroupRepository = workGroupRepository;
        this.exerciseRepository = exerciseRepository;
        this.userWorkoutPlanRepository = userWorkoutPlanRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, UserDataSheetPlanRequest userDataSheetPlanRequest) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        Contract contract = contractRepository.findByExternalId(userDataSheetPlanRequest.getContract().getExternalId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid contract", HttpStatus.BAD_REQUEST, "MSG-1632"));

        if(!Objects.equals(contract.getTrainingPack().getPersonalUser().getId(), trainer.getId())){
            throw new CommoditieBaseException("Invalid contract", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1840");
        }

        Modality modality = modalityRepository.findById(userDataSheetPlanRequest.getModality().getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Modality", HttpStatus.BAD_REQUEST, "MSG-1636"));

        Goal goal = goalRepository.findById(userDataSheetPlanRequest.getGoal().getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Goal", HttpStatus.BAD_REQUEST, "MSG-1637"));

        Program program;
        if (userDataSheetPlanRequest.getProgram() != null) {
            program = programRepository.findById(userDataSheetPlanRequest.getProgram().getId())
                    .orElseThrow(() -> new CommoditieBaseException("Invalid program", HttpStatus.BAD_REQUEST, "MSG-1642"));
        } else {
            program = null;
        }

        List<UserWorkoutPlan> toSave = new ArrayList<>();

        Map<String, List<UserWorkoutPlanRequest>> allWorkoutPlan = userDataSheetPlanRequest.getPlan();
        allWorkoutPlan.forEach((workgroupKey, exercisesList) -> {
            log.info("({}) workgroup => {}", processId, workgroupKey);
            List<UserWorkoutPlan> collect = exercisesList
                    .stream()
                    .map(exerciseItem -> getUserWorkoutPlanEntity(exerciseItem, contract, modality, goal, program))
                    .collect(Collectors.toList());
            toSave.addAll(collect);

        });

        userWorkoutPlanRepository.disableExercises(contract.getId());
        userWorkoutPlanRepository.saveAll(toSave);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1628",
                "Your Workout Plan has been saved successfully",
                Boolean.TRUE
        );
    }

    private UserWorkoutPlan getUserWorkoutPlanEntity(UserWorkoutPlanRequest exerciseItem,
                                                     Contract contract,
                                                     Modality modality,
                                                     Goal goal,
                                                     Program program) {
        WorkGroup workgroup = workGroupRepository.findById(exerciseItem.getWorkGroup().getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Workgroup", HttpStatus.BAD_REQUEST, "MSG-1704"));

        Exercise exercise = null;
        if (exerciseItem.getExercise() != null) {
            exercise = exerciseRepository.findById(exerciseItem.getExercise().getId())
                    .orElseThrow(() -> new CommoditieBaseException("Invalid exercise", HttpStatus.BAD_REQUEST, "MSG-1706"));
        }

        return UserWorkoutPlan.builder()
                .externalId(UUID.randomUUID())
                .contract(contract)
                .modality(modality)
                .goal(goal)
                .program(program)
                .workGroup(workgroup)
                .exercise(exercise)
                .customExercise(exerciseItem.getCustomExercise())
                .customProgram(exerciseItem.getCustomProgram())
                .executionMethod(exerciseItem.getExecutionMethod())
                .qtyReps(exerciseItem.getQtyReps())
                .qtySeries(exerciseItem.getQtySeries())
                .execution(exerciseItem.getExecution())
                .executionTime(exerciseItem.getExecutionTime())
                .restTime(exerciseItem.getRestTime())
                .comments(exerciseItem.getComments())
                .obs(exerciseItem.getObs())
                .status(StatusEnum.A)
                .build();
    }

}
