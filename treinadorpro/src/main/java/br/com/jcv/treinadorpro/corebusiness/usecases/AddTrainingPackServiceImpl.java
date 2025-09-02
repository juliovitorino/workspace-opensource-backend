package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.AddTrainingPackRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AddTrainingPackServiceImpl implements AddTrainingPackService{

    private final GetLoggedUserService getLoggedUserService;
    private final UserRepository userRepository;
    private final ModalityRepository modalityRepository;
    private final TrainingPackRepository trainingPackRepository;

    public AddTrainingPackServiceImpl(GetLoggedUserService getLoggedUserService,
                                      UserRepository userRepository,
                                      ModalityRepository modalityRepository,
                                      TrainingPackRepository trainingPackRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.userRepository = userRepository;
        this.modalityRepository = modalityRepository;
        this.trainingPackRepository = trainingPackRepository;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, AddTrainingPackRequest request) {
        getLoggedUserService.execute(processId);
        trainingPackRepository.save(getTrainingPackInstance(request, getLoggedUserService.execute(processId)));

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1008",
                "Your training pack has been saved",
                Boolean.TRUE
        );    }

    private TrainingPack getTrainingPackInstance(AddTrainingPackRequest request, PersonalTrainerResponse trainer) {
        User user = userRepository.findById(trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Trainer", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-0854"));

        Modality modality = modalityRepository.findById(request.getModalityId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Modality", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-0856"));
        return TrainingPack.builder()
                .externalId(UUID.randomUUID())
                .personalUser(user)
                .modality(modality)
                .description(request.getDescription())
                .durationDays(request.getDurationDays())
                .weeklyFrequency(request.getWeeklyFrequency())
                .notes(request.getNotes())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .status("A")
                .build();
    }
}
