package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.mapper.UserPackTrainingMapper;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserPackTrainingRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllMyStudentsServiceImpl implements FindAllMyStudentsService{

    private final UserPackTrainingRepository userPackTrainingRepository;
    private final UserRepository userRepository;
    private final UserPackTrainingMapper userPackTrainingMapper;

    public FindAllMyStudentsServiceImpl(UserPackTrainingRepository userPackTrainingRepository, UserRepository userRepository, UserPackTrainingMapper userPackTrainingMapper) {
        this.userPackTrainingRepository = userPackTrainingRepository;
        this.userRepository = userRepository;
        this.userPackTrainingMapper = userPackTrainingMapper;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<List<UserPackTrainingDTO>> execute(UUID processId, Long personalUserId) {
        User personalUser = userRepository.findById(personalUserId)
                .orElseThrow(() -> new CommoditieBaseException("Invalid personalUserId", HttpStatus.BAD_REQUEST));
        List<UserPackTrainingDTO> collectedUserPackTraining = userPackTrainingRepository.findByPersonalUser(personalUser)
                .stream()
                .map(userPackTrainingMapper::toDTO)
                .collect(Collectors.toList());

        ControllerGenericResponse<List<UserPackTrainingDTO>> response = new ControllerGenericResponse<>();
        response.setObjectResponse(collectedUserPackTraining);
        response.setResponse(MensagemResponse.builder()
                        .msgcode("MSG-0001")
                        .mensagem("Command has been executed successfully")
                .build());

        return response;
    }
}
