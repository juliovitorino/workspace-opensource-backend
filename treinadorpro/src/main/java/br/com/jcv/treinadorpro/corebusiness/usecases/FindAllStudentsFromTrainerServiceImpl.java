package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.AbstractTreinadorProService;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserPackTrainingRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllStudentsFromTrainerServiceImpl extends AbstractTreinadorProService implements FindAllStudentsFromTrainerService {

    private final TrainingPackRepository trainingPackRepository;
    private final UserPackTrainingRepository userPackTrainingRepository;

    public FindAllStudentsFromTrainerServiceImpl(TrainingPackRepository trainingPackRepository,
                                                 UserRepository userRepository, UserPackTrainingRepository userPackTrainingRepository) {
        super(userRepository, trainingPackRepository);
        this.trainingPackRepository = trainingPackRepository;
        this.userPackTrainingRepository = userPackTrainingRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<List<StudentsFromTrainerResponse>> execute(UUID processId, UUID personalTrainerExternalId) {

        User trainerUser = checkActivePersonalTrainerUUID(personalTrainerExternalId);

        List<User> distinctStudentsByPersonalTrainerTrainingPacks =
                userPackTrainingRepository.findDistinctStudentsByPersonalTrainerTrainingPacks(trainerUser.getId());

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1116",
                "All Students were retireved successfully",
                distinctStudentsByPersonalTrainerTrainingPacks.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }

    private StudentsFromTrainerResponse toResponse(User user) {
        return StudentsFromTrainerResponse.builder()
                .externalId(user.getUuidId())
                .name(user.getName())
                .build();
    }
}
