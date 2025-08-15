package br.com.jcv.treinadorpro.corebusiness.trainingpack;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.CreateTrainingPackRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper.toEntity;

@Service
@Deprecated
public class CreateTrainingPackServiceImpl implements CreateTrainingPackService{

    private final TrainingPackRepository trainingPackRepository;
    private final UserRepository userRepository;
    private final ModalityRepository modalityRepository;

    public CreateTrainingPackServiceImpl(TrainingPackRepository trainingPackRepository,
                                         UserRepository userRepository,
                                         ModalityRepository modalityRepository) {
        this.trainingPackRepository = trainingPackRepository;
        this.userRepository = userRepository;
        this.modalityRepository = modalityRepository;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, CreateTrainingPackRequest createTrainingPackRequest) {
        User user = userRepository.findByUuidId(createTrainingPackRequest.getPersonalUserUUID())
                .orElseThrow(() -> new CommoditieBaseException("Invalid External UUID for personal trainer", HttpStatus.BAD_REQUEST, "MSG-1700"));

        Modality modality = modalityRepository.findById(createTrainingPackRequest.getModalityId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Modality", HttpStatus.BAD_REQUEST, "MSG-1702"));

        TrainingPack trainingPack = toEntity(createTrainingPackRequest, user,  modality);
        assert trainingPack != null;
        trainingPackRepository.save(trainingPack);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1655",
                "Training Pack has been included successfully",
                Boolean.TRUE
        );
    }

}
