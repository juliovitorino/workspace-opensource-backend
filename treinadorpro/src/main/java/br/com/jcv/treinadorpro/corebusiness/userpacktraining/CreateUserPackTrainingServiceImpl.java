package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.mapper.ModalityMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.UserPackTrainingMapper;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.UserPackTraining;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutCalendar;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityExerciseRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserPackTrainingRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutCalendarRepository;
import br.com.jcv.treinadorpro.corelayer.request.UserPackTrainingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateUserPackTrainingServiceImpl implements CreateUserPackTrainingService {
    private final UserPackTrainingMapper userPackTrainingMapper;
    private final UserPackTrainingRepository userPackTrainingRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ModalityMapper modalityMapper;
    private final ModalityRepository modalityRepository;
    private final UserWorkoutCalendarRepository userWorkoutCalendarRepository;
    private final ModalityExerciseRepository modalityExerciseRepository;

    public CreateUserPackTrainingServiceImpl(UserPackTrainingMapper userPackTrainingMapper,
                                             UserPackTrainingRepository userPackTrainingRepository,
                                             UserRepository userRepository,
                                             UserMapper userMapper,
                                             ModalityMapper modalityMapper,
                                             ModalityRepository modalityRepository, UserWorkoutCalendarRepository userWorkoutCalendarRepository, ModalityExerciseRepository modalityExerciseRepository) {
        this.userPackTrainingMapper = userPackTrainingMapper;
        this.userPackTrainingRepository = userPackTrainingRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.modalityMapper = modalityMapper;
        this.modalityRepository = modalityRepository;
        this.userWorkoutCalendarRepository = userWorkoutCalendarRepository;
        this.modalityExerciseRepository = modalityExerciseRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<UserPackTrainingDTO> execute(UUID processId, UserPackTrainingRequest request) {

        UserPackTrainingDTO instanceUserPackTrainingDTO = getInstanceUserPackTrainingDTO(request);

        UserPackTraining userPackTrainingMapperEntity = userPackTrainingMapper.toEntity(instanceUserPackTrainingDTO);
        UserPackTraining userPackTrainingSaved = userPackTrainingRepository.save(userPackTrainingMapperEntity);

        List<UserWorkoutCalendar> listInstanceUserWorkoutCalendar = getListInstanceUserWorkoutCalendar(request, userPackTrainingSaved);
        userWorkoutCalendarRepository.saveAll(listInstanceUserWorkoutCalendar);

        ControllerGenericResponse<UserPackTrainingDTO> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                        .msgcode("MSG-0001")
                        .mensagem("Command has been executed successfully")
                .build());

        return response;
    }

    private List<UserWorkoutCalendar> getListInstanceUserWorkoutCalendar(UserPackTrainingRequest request, UserPackTraining userPackTrainingSaved) {
        return request.getUserWorkoutCalendar().stream().map( calendarItem-> {
            UserWorkoutCalendar userWorkoutCalendar = new UserWorkoutCalendar();
            userWorkoutCalendar.setUserPackTraining(userPackTrainingSaved);
            userWorkoutCalendar.setTrainingDate(calendarItem.getTrainingDate());
            userWorkoutCalendar.setStartTime(request.getStartTime());
            userWorkoutCalendar.setEndTime(request.getEndTime());
            userWorkoutCalendar.setExecution(calendarItem.getExecution());
            userWorkoutCalendar.setModalityExercise(
                    modalityExerciseRepository.findById(calendarItem.getModalityExerciseId())
                            .orElseThrow(() -> new CommoditieBaseException("Invalid ModalityExercise ID",HttpStatus.BAD_REQUEST)));
            return userWorkoutCalendar;
        }).collect(Collectors.toList());
    }

    private UserPackTrainingDTO getInstanceUserPackTrainingDTO(UserPackTrainingRequest request) {
        User personalUser = userRepository.findById(request.getPersonalUserId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Personal User Id", HttpStatus.BAD_REQUEST));

        User studentUser = userRepository.findById(request.getStudentUserId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Student User Id", HttpStatus.BAD_REQUEST));

        Modality modality = modalityRepository.findById(request.getModalityId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Modality Id", HttpStatus.BAD_REQUEST));

        UserPackTrainingDTO instance = new UserPackTrainingDTO();

        instance.setPersonalUser(userMapper.toDTO(personalUser));
        instance.setStudentUser(userMapper.toDTO(studentUser));
        instance.setModality(modalityMapper.toDTO(modality));
        instance.setDescription(request.getDescription());
        instance.setPrice(request.getPrice());
        instance.setStartTime(request.getStartTime());
        instance.setEndTime(request.getEndTime());
        instance.setDaysOfWeek(request.getDaysOfWeek());

        return instance;
    }
}
