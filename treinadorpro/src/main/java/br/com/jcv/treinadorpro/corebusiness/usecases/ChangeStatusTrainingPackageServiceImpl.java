package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.request.ChangeStatusTrainingPackageRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ChangeStatusTrainingPackageServiceImpl implements ChangeStatusTrainingPackageService{

    private final GetLoggedUserService getLoggedUserService;
    private final TrainingPackRepository trainingPackRepository;

    public ChangeStatusTrainingPackageServiceImpl(GetLoggedUserService getLoggedUserService, TrainingPackRepository trainingPackRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.trainingPackRepository = trainingPackRepository;
    }

    @Override
    public void execute(UUID processId, ChangeStatusTrainingPackageRequest request) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        TrainingPack trainingPack = trainingPackRepository.findByExternalIdAndPersonalUserId(request.getExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Training Package", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1421"));

        trainingPack.setStatus(request.getStatus().toString());
        trainingPackRepository.save(trainingPack);


    }
}
