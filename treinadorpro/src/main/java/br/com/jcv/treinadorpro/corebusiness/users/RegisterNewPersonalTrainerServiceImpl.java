package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.infrastructure.config.TreinadorProConfig;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class RegisterNewPersonalTrainerServiceImpl implements RegisterNewPersonalTrainerService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TreinadorProConfig config;
    private final ModelMapper modelMapper;
    private final GuardianRestClientConsumer guardianRestClientConsumer;

    public RegisterNewPersonalTrainerServiceImpl(UserRepository userRepository,
                                                 UserMapper userMapper,
                                                 TreinadorProConfig config,
                                                 ModelMapper modelMapper,
                                                 GuardianRestClientConsumer guardianRestClientConsumer) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.config = config;
        this.modelMapper = modelMapper;
        this.guardianRestClientConsumer = guardianRestClientConsumer;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<UserDTO> execute(UUID processId, RegisterRequest registerRequest) {

        User userByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (Objects.nonNull(userByEmail)) {
            throw new CommoditieBaseException("Email has already exist!", HttpStatus.BAD_REQUEST,"MSG-1922");
        }

        CreateNewAccountRequest createNewAccountRequest = getInstanceCreateNewAccountRequest(registerRequest);
        ControllerGenericResponse<UUID> accountGuardianResponse = guardianRestClientConsumer.createNewAccount(createNewAccountRequest);

        // mapping user data for local process
        UserDTO userDTO = getInstanceUserDTO(registerRequest, accountGuardianResponse);

        User userEntity = userMapper.toEntity(userDTO);
        User userSaved = userRepository.save(userEntity);

        ControllerGenericResponse<UserDTO> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                        .msgcode("MSG-0001")
                        .mensagem(accountGuardianResponse.getResponse().getMensagem())
                .build());
        response.setObjectResponse(userMapper.toDTO(userSaved));

        return response;
    }

    private UserDTO getInstanceUserDTO(RegisterRequest registerRequest, ControllerGenericResponse<UUID> accountGuardianResponse) {
        UserDTO userDTO = modelMapper.map(registerRequest, UserDTO.class);
        userDTO.setUserProfile(UserProfileEnum.PERSONAL_TRAINER);
        userDTO.setStatus("A");
        userDTO.setGuardianIntegrationUUID(accountGuardianResponse.getObjectResponse());
        return userDTO;
    }

    private CreateNewAccountRequest getInstanceCreateNewAccountRequest(RegisterRequest registerRequest) {
        CreateNewAccountRequest createNewAccountRequest = new CreateNewAccountRequest();
        createNewAccountRequest.setEmail(registerRequest.getEmail());
        createNewAccountRequest.setPasswd(registerRequest.getPasswd());
        createNewAccountRequest.setPasswdCheck(registerRequest.getPasswdCheck());
        createNewAccountRequest.setExternalApplicationUUID(config.getApiKeyUUID());
        createNewAccountRequest.setBirthday(registerRequest.getBirthday());
        createNewAccountRequest.setName(
                String.join(" ",
                        registerRequest.getFirstName(),
                        registerRequest.getMiddleName(),
                        registerRequest.getLastName()
                ).trim()
        );
        return createNewAccountRequest;
    }
}
