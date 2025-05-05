package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.mapper.ModalityMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.UserPackTrainingMapper;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.UserPackTraining;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutCalendar;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserPackTrainingRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutCalendarRepository;
import br.com.jcv.treinadorpro.corelayer.request.UserPackTrainingRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateUserPackTrainingServiceImpl implements CreateUserPackTrainingService {
    private final UserPackTrainingRepository userPackTrainingRepository;
    private final UserRepository userRepository;

    public CreateUserPackTrainingServiceImpl(
                                             UserPackTrainingRepository userPackTrainingRepository,
                                             UserRepository userRepository
    ) {
        this.userPackTrainingRepository = userPackTrainingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, UserPackTrainingRequest request) {

        UserPackTraining userPackTrainingMapperEntity = getUserPackTrainingEntity(request);
        userPackTrainingRepository.save(userPackTrainingMapperEntity);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-0001",
                "Your User Pack Training has been saved",
                Boolean.TRUE);
    }

    private UserPackTraining getUserPackTrainingEntity(UserPackTrainingRequest request) {
        return UserPackTraining.builder()
                .personalUser(userRepository.findById(request.getPersonalUserId()).orElseThrow(() -> new CommoditieBaseException( "Invalid Personal User", HttpStatus.BAD_REQUEST, "MSG-1103")))
                .studentUser(userRepository.findById(request.getStudentUserId()).orElseThrow(() -> new CommoditieBaseException( "Invalid Student User", HttpStatus.BAD_REQUEST, "MSG-1105")))
                .description(request.getDescription())
                .externalId(UUID.randomUUID())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .daysOfWeek(request.getDaysOfWeek().toArray(new Integer[0]))
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(StatusEnum.A)
                .build();
    }

}
