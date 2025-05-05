package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.PersonalTrainerProgram;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.WorkGroupExercise;
import br.com.jcv.treinadorpro.corelayer.repository.PersonalTrainerProgramRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.repository.WorkGroupExerciseRepository;
import br.com.jcv.treinadorpro.corelayer.request.EditPersonalTrainerProgramRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class EditPersonalTrainerProgramServiceImpl extends  AbstractUserService implements EditPersonalTrainerProgramService {

    private final PersonalTrainerProgramRepository personalTrainerProgramRepository;
    private final WorkGroupExerciseRepository workGroupExerciseRepository;

    protected EditPersonalTrainerProgramServiceImpl(UserRepository userRepository,
                                                    PersonalTrainerProgramRepository personalTrainerProgramRepository,
                                                    WorkGroupExerciseRepository workGroupExerciseRepository) {
        super(userRepository);
        this.personalTrainerProgramRepository = personalTrainerProgramRepository;
        this.workGroupExerciseRepository = workGroupExerciseRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, EditPersonalTrainerProgramRequest request) {

        checkPersonalTrainerUUID(request.getPersonalUserUUID());
        User userByUUID = getUserByUUID(request.getPersonalUserUUID());

        PersonalTrainerProgram personalTrainerProgram = validations(request, userByUUID);

        WorkGroupExercise workGroupExercise = workGroupExerciseRepository.findByWorkGroupIdAndExerciseId(request.getWorkGroupId(), request.getExerciseId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid work group x exercise", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1556"));

        mapper(request, personalTrainerProgram, workGroupExercise);
        personalTrainerProgramRepository.save(personalTrainerProgram);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-0001",
                "Your Personal Trainer Program has been updated.",
                Boolean.TRUE);
    }

    private void mapper(EditPersonalTrainerProgramRequest source, PersonalTrainerProgram target, WorkGroupExercise workGroupExercise) {
        target.setWorkGroup(workGroupExercise.getWorkGroup());
        target.setExercise(workGroupExercise.getExercise());
        target.setCustomProgram(source.getCustomProgram());
        target.setCustomExercise(source.getCustomExercise());
        target.setExecutionMethod(source.getExecutionMethod());
        target.setQtySeries(source.getQtySeries());
        target.setQtyReps(source.getQtyReps());
        target.setExecution(source.getExecution());
        target.setExecutionTime(source.getExecutionTime());
        target.setRestTime(source.getRestTime());
        target.setWeight(source.getWeight());
        target.setWeightUnit(source.getWeightUnit());
        target.setComments(source.getComments());
        target.setObs(source.getObs());
    }

    private PersonalTrainerProgram validations(EditPersonalTrainerProgramRequest request, User userByUUID) {
        PersonalTrainerProgram personalTrainerProgram = personalTrainerProgramRepository.findById(request.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Personal Program Item", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1429"));

        if (!Objects.equals(personalTrainerProgram.getPersonalUser().getId(), userByUUID.getId())) {
            throw new CommoditieBaseException("This program not belong you", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1431");
        }
        return personalTrainerProgram;
    }

}
