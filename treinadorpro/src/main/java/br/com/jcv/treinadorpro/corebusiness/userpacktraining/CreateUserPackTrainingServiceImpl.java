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
import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserPackTrainingRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.UserPackTrainingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateUserPackTrainingServiceImpl implements CreateUserPackTrainingService {
    private final UserPackTrainingMapper userPackTrainingMapper;
    private final UserPackTrainingRepository userPackTrainingRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ModalityMapper modalityMapper;
    private final ModalityRepository modalityRepository;

    public CreateUserPackTrainingServiceImpl(UserPackTrainingMapper userPackTrainingMapper,
                                             UserPackTrainingRepository userPackTrainingRepository,
                                             UserRepository userRepository,
                                             UserMapper userMapper,
                                             ModalityMapper modalityMapper,
                                             ModalityRepository modalityRepository) {
        this.userPackTrainingMapper = userPackTrainingMapper;
        this.userPackTrainingRepository = userPackTrainingRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.modalityMapper = modalityMapper;
        this.modalityRepository = modalityRepository;
    }

    @Override
    public ControllerGenericResponse<UserPackTrainingDTO> execute(UUID processId, UserPackTrainingRequest request) {

        UserPackTrainingDTO instanceUserPackTrainingDTO = getInstanceUserPackTrainingDTO(request);

        UserPackTraining userPackTrainingMapperEntity = userPackTrainingMapper.toEntity(instanceUserPackTrainingDTO);
        UserPackTraining userPackTrainingSaved = userPackTrainingRepository.save(userPackTrainingMapperEntity);

        ControllerGenericResponse<UserPackTrainingDTO> response = new ControllerGenericResponse<>();
        response.setObjectResponse(userPackTrainingMapper.toDTO(userPackTrainingSaved));
        response.setResponse(MensagemResponse.builder()
                        .msgcode("MSG-0001")
                        .mensagem("Command has been executed successfully")
                .build());

        return response;
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
