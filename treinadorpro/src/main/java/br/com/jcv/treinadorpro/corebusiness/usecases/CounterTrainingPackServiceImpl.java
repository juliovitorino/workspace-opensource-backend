package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.service.BusinessServiceNoInput;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CounterTrainingPackServiceImpl implements BusinessServiceNoInput<Long> {

    private final TrainingPackRepository trainingPackRepository;
    private final GetLoggedUserService getLoggedUserService;

    public CounterTrainingPackServiceImpl(TrainingPackRepository trainingPackRepository, GetLoggedUserService getLoggedUserService) {
        this.trainingPackRepository = trainingPackRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    public Long execute(UUID processId) {
        return trainingPackRepository.countTrainingPack(getLoggedUserService.execute(processId).getId());
    }
}
