package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.model.StudentFeature;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.StudentFeatureRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.PasswordHasher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateStudentAccountServiceImpl extends AbstractUserService implements CreateStudentAccountService {

    private final static String INITIAL_PASSWORD = "123456";

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final StudentFeatureRepository studentFeatureRepository;


    public CreateStudentAccountServiceImpl(ModelMapper modelMapper,
                                           UserMapper userMapper,
                                           UserRepository userRepository,
                                           StudentFeatureRepository studentFeatureRepository) {
        super(userRepository);
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.studentFeatureRepository = studentFeatureRepository;
    }

    @Override
    public ControllerGenericResponse<UserDTO> execute(UUID processId, RegisterRequest registerRequest) {
        checkExistingEmail(registerRequest);

        User userEntity = getInstanceUserEntity(registerRequest);
        User userSaved = userRepository.save(userEntity);

        StudentFeature instanceStudentFeatureEntity = getInstanceStudentFeatureEntity(userSaved, registerRequest);
        studentFeatureRepository.save(instanceStudentFeatureEntity);

        ControllerGenericResponse<UserDTO> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                .msgcode("MSG-0001")
                .mensagem("Your student account has been created. Initial password " + INITIAL_PASSWORD)
                .build());

        return response;
    }

    private StudentFeature getInstanceStudentFeatureEntity(User userSaved, RegisterRequest registerRequest) {
        return StudentFeature.builder()
                .studentUser(userSaved)
                .password(PasswordHasher.hashSHA256(INITIAL_PASSWORD))
                .status(StatusEnum.A)
                .build();
    }

    private User getInstanceUserEntity(RegisterRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);
        user.setUserProfile(UserProfileEnum.STUDENT);
        user.setStatus("A");
        return user;
    }

}
