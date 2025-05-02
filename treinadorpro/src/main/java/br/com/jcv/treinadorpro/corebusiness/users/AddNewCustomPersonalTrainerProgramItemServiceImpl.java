package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.Exercise;
import br.com.jcv.treinadorpro.corelayer.model.Goal;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.PersonalTrainerProgram;
import br.com.jcv.treinadorpro.corelayer.model.Program;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.WorkGroup;
import br.com.jcv.treinadorpro.corelayer.repository.ExerciseRepository;
import br.com.jcv.treinadorpro.corelayer.repository.GoalRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.PersonalTrainerProgramRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ProgramRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.repository.WorkGroupRepository;
import br.com.jcv.treinadorpro.corelayer.request.PersonalTrainerProgramRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class AddNewCustomPersonalTrainerProgramItemServiceImpl extends AbstractUserService implements AddNewCustomPersonalTrainerProgramItemService {

    private final ModalityRepository modalityRepository;
    private final GoalRepository goalRepository;
    private final ProgramRepository programRepository;
    private final ExerciseRepository exerciseRepository;
    private final PersonalTrainerProgramRepository personalTrainerProgramRepository;
    private final WorkGroupRepository workGroupRepository;

    protected AddNewCustomPersonalTrainerProgramItemServiceImpl(UserRepository userRepository,
                                                                ModalityRepository modalityRepository,
                                                                GoalRepository goalRepository,
                                                                ProgramRepository programRepository,
                                                                ExerciseRepository exerciseRepository,
                                                                PersonalTrainerProgramRepository personalTrainerProgramRepository,
                                                                WorkGroupRepository workGroupRepository) {
        super(userRepository);
        this.modalityRepository = modalityRepository;
        this.goalRepository = goalRepository;
        this.programRepository = programRepository;
        this.exerciseRepository = exerciseRepository;
        this.personalTrainerProgramRepository = personalTrainerProgramRepository;
        this.workGroupRepository = workGroupRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, PersonalTrainerProgramRequest request) {

        checkPersonalTrainerUUID(request.getPersonalUserUUID());

        User userByUUID = getUserByUUID(request.getPersonalUserUUID());
        Modality modality = getModality(request.getModalityId());
        Goal goal = getGoal(request.getGoalId());
        WorkGroup workgroup = getWorkgroup(request.getWorkGroupId());
        Program program = getProgram(request.getProgramId());
        Exercise exercise = getExercise(request.getExerciseId());

        PersonalTrainerProgram personalTrainerProgramEntity = getPersonalTrainerProgramEntity(userByUUID, modality, goal, program, workgroup, exercise, request);
        personalTrainerProgramRepository.save(personalTrainerProgramEntity);


        return ControllerGenericResponseHelper.getInstance(
                "MSG-0001",
                "You have just added new custom exercise",
                Boolean.TRUE);
    }


    private PersonalTrainerProgram getPersonalTrainerProgramEntity(User userByUUID, Modality modality, Goal goal, Program program, WorkGroup workgroup, Exercise exercise, PersonalTrainerProgramRequest request) {
        return PersonalTrainerProgram.builder()
                .personalUser(userByUUID)
                .modality(modality)
                .goal(goal)
                .program(program)
                .workGroup(workgroup)
                .exercise(exercise)
                .customExercise(request.getCustomExercise())
                .customProgram(request.getCustomProgram())
                .executionMethod(request.getExecutionMethod())
                .execution(request.getExecution())
                .executionTime(request.getExecutionTime())
                .restTime(request.getRestTime())
                .weight(request.getWeight())
                .weightUnit(request.getWeightUnit())
                .comments(request.getComments())
                .obs(request.getObs())
                .status(StatusEnum.A)
                .build();
    }

    private WorkGroup getWorkgroup(Long workGroupId) {
        return workGroupRepository.findById(workGroupId)
                .orElseThrow(() -> new CommoditieBaseException("Invalid workgroup", HttpStatus.BAD_REQUEST, "MSG-1704"));
    }

    private Exercise getExercise(Long exerciseId) {
        return Objects.isNull(exerciseId)
                ? null
                : exerciseRepository.findById(exerciseId).orElse(null);
    }

    private Program getProgram(Long programId) {
        return Objects.isNull(programId)
                ? null
                : programRepository.findById(programId).orElse(null);
    }

    private Modality getModality(Long modalityId) {
        return modalityRepository.findById(modalityId)
                .orElseThrow(() -> new CommoditieBaseException("Invalid Modality", HttpStatus.BAD_REQUEST, "MSG-1645"));
    }

    private Goal getGoal(Long goalId) {
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new CommoditieBaseException("Invalid Goal", HttpStatus.BAD_REQUEST, "MSG-1647"));
    }
}
