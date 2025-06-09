package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutCalendar;
import br.com.jcv.treinadorpro.corelayer.repository.ExerciseRepository;
import br.com.jcv.treinadorpro.corelayer.repository.GoalRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ProgramRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserPackTrainingRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutCalendarRepository;
import br.com.jcv.treinadorpro.corelayer.repository.WorkGroupRepository;
import br.com.jcv.treinadorpro.corelayer.request.EditUserWorkoutCalendarItemRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EditUserPackTrainingItemServiceImpl implements EditUserPackTrainingItemService {

    private final UserWorkoutCalendarRepository userWorkoutCalendarRepository;
    private final UserPackTrainingRepository userPackTrainingRepository;
    private final ModalityRepository modalityRepository;
    private final GoalRepository goalRepository;
    private final ProgramRepository programRepository;
    private final WorkGroupRepository workGroupRepository;
    private final ExerciseRepository exerciseRepository;

    public EditUserPackTrainingItemServiceImpl(UserWorkoutCalendarRepository userWorkoutCalendarRepository,
                                               UserPackTrainingRepository userPackTrainingRepository,
                                               ModalityRepository modalityRepository,
                                               GoalRepository goalRepository,
                                               ProgramRepository programRepository,
                                               WorkGroupRepository workGroupRepository,
                                               ExerciseRepository exerciseRepository) {
        this.userWorkoutCalendarRepository = userWorkoutCalendarRepository;
        this.userPackTrainingRepository = userPackTrainingRepository;
        this.modalityRepository = modalityRepository;
        this.goalRepository = goalRepository;
        this.programRepository = programRepository;
        this.workGroupRepository = workGroupRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, EditUserWorkoutCalendarItemRequest request) {

        UserWorkoutCalendar workoutCalendarItem = userWorkoutCalendarRepository.findByExternalId(request.getExternalId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Workout calendar item", HttpStatus.BAD_REQUEST, "MSG-0852"));

        this.map(request, workoutCalendarItem);
        workoutCalendarItem.setUserPackTraining(userPackTrainingRepository.findByExternalId(request.getUserPackTrainingId()).orElseThrow(() -> new CommoditieBaseException("Invalid User Pack Training", HttpStatus.BAD_REQUEST, "MSG-0857")));
        workoutCalendarItem.setModality(modalityRepository.findById(request.getModality()).orElseThrow(() -> new CommoditieBaseException("Invalid Modality", HttpStatus.BAD_REQUEST, "MSG-0859")));
        workoutCalendarItem.setGoal(goalRepository.findById(request.getGoal()).orElseThrow(() -> new CommoditieBaseException("Invalid Goal", HttpStatus.BAD_REQUEST, "MSG-0900")));
        workoutCalendarItem.setProgram(programRepository.findById(request.getProgram()).orElseThrow(() -> new CommoditieBaseException("Invalid Program", HttpStatus.BAD_REQUEST, "MSG-0901")));
//        workoutCalendarItem.setWorkGroup(workGroupRepository.findById(request.getWorkGroup()).orElseThrow(() -> new CommoditieBaseException("Invalid WorkGroup", HttpStatus.BAD_REQUEST, "MSG-0902")));
        workoutCalendarItem.setExercise(exerciseRepository.findById(request.getExercise()).orElseThrow(() -> new CommoditieBaseException("Invalid Exercise", HttpStatus.BAD_REQUEST, "MSG-0903")));

        userWorkoutCalendarRepository.save(workoutCalendarItem);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-0839",
                "User Workout Item has been updated",
                Boolean.TRUE
        );
    }

    private void map(EditUserWorkoutCalendarItemRequest request, UserWorkoutCalendar workoutCalendarItem) {
        workoutCalendarItem.setTrainingDate(request.getTrainingDate());
        workoutCalendarItem.setCustomExercise(request.getCustomExercise());
        workoutCalendarItem.setCustomProgram(request.getCustomProgram());
        workoutCalendarItem.setExecutionMethod(request.getExecutionMethod());
        workoutCalendarItem.setQtySeries(request.getQtySeries());
        workoutCalendarItem.setQtyReps(request.getQtyReps());
        workoutCalendarItem.setExecution(request.getExecution());
        workoutCalendarItem.setExecutionTime(request.getExecutionTime());
        workoutCalendarItem.setRestTime(request.getRestTime());
        workoutCalendarItem.setWeight(request.getWeight());
        workoutCalendarItem.setWeightUnit(request.getWeightUnit());
        workoutCalendarItem.setComments(request.getComments());
        workoutCalendarItem.setObs(request.getObs());
    }
}
