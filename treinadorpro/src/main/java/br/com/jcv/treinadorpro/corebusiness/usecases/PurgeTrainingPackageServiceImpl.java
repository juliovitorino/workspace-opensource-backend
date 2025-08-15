package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class PurgeTrainingPackageServiceImpl implements PurgeTrainingPackageService{

    private final GetLoggedUserService getLoggedUserService;
    private final TrainingPackRepository trainingPackRepository;

    public PurgeTrainingPackageServiceImpl(GetLoggedUserService getLoggedUserService,
                                           TrainingPackRepository trainingPackRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.trainingPackRepository = trainingPackRepository;
    }

    @Override
    @Transactional
    public void execute(UUID processId, UUID trainingPackageExternalId) {

        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        TrainingPack trainingPack = trainingPackRepository.findByExternalIdAndPersonalUserId(trainingPackageExternalId, trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Training Package", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1604"));
        trainingPackRepository.deleteUsingId(trainingPack.getId());

    }
}
