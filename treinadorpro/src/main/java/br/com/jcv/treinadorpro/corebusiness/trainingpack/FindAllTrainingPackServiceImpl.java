package br.com.jcv.treinadorpro.corebusiness.trainingpack;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllTrainingPackServiceImpl implements FindAllTrainingPackService{

    private final TrainingPackRepository trainingPackRepository;
    private final UserRepository userRepository;

    public FindAllTrainingPackServiceImpl(TrainingPackRepository trainingPackRepository,
                                          UserRepository userRepository) {
        this.trainingPackRepository = trainingPackRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ControllerGenericResponse<List<TrainingPackResponse>> execute(UUID processId, UUID trainerExternalUUID) {
        User user = userRepository.findByUuidId(trainerExternalUUID)
                .orElseThrow(() -> new CommoditieBaseException("Invalid Personal ID", HttpStatus.BAD_REQUEST, "MSG-1737"));

        List<TrainingPack> allTrainingPack = trainingPackRepository.findAllByPersonalUserId(user.getId());
        List<TrainingPackResponse> trainingPackResponseList = allTrainingPack.stream()
                .map(MapperServiceHelper::toResponse)
                .collect(Collectors.toList());

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1743",
                "Training pack list was retrieve successfully",
                trainingPackResponseList
        );
    }


}
