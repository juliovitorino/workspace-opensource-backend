package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.AbstractTreinadorProService;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.config.TreinadorProConfig;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllStudentsFromTrainerServiceImpl extends AbstractTreinadorProService implements FindAllStudentsFromTrainerService {

    private final TrainingPackRepository trainingPackRepository;
    private final ContractRepository contractRepository;
    private final TreinadorProConfig config;

    public FindAllStudentsFromTrainerServiceImpl(TrainingPackRepository trainingPackRepository,
                                                 UserRepository userRepository,
                                                 ContractRepository contractRepository,
                                                 TreinadorProConfig config) {
        super(userRepository, trainingPackRepository, config);
        this.trainingPackRepository = trainingPackRepository;
        this.contractRepository = contractRepository;
        this.config = config;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<List<StudentsFromTrainerResponse>> execute(UUID processId, UUID personalTrainerExternalId) {

        User trainerUser = checkActivePersonalTrainerUUID(personalTrainerExternalId);

        List<User> distinctStudentsByPersonalTrainerTrainingPacks =
                contractRepository.findDistinctStudentsByPersonalTrainerTrainingPacks(trainerUser.getId());

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1116",
                "All Students were retireved successfully",
                distinctStudentsByPersonalTrainerTrainingPacks.stream()
                        .map(MapperServiceHelper::toResponseStudents)
                        .collect(Collectors.toList())
        );
    }


}
