package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.AbstractTreinadorProService;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.config.TreinadorProConfig;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllTrainingPackFromTrainerServiceImpl
        extends AbstractTreinadorProService implements FindAllTrainingPackFromTrainerService{

    private final UserRepository userRepository;
    private final TrainingPackRepository trainingPackRepository;
    private final TreinadorProConfig config;

    public FindAllTrainingPackFromTrainerServiceImpl(UserRepository userRepository,
                                                     TrainingPackRepository trainingPackRepository,
                                                     TreinadorProConfig config) {
        super(userRepository, trainingPackRepository, config);
        this.userRepository = userRepository;
        this.trainingPackRepository = trainingPackRepository;
        this.config = config;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<List<TrainingPackResponse>> execute(UUID processId, UUID personalTrainerExternalId) {
        User trainerUser = checkActivePersonalTrainerUUID(personalTrainerExternalId);
        List<TrainingPack> trainingPackFromTrainerList = trainingPackRepository.findAllByPersonalUserId(trainerUser.getId());

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1640",
                "All Training Pack were retrieved successfully",
                trainingPackFromTrainerList
                        .stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
