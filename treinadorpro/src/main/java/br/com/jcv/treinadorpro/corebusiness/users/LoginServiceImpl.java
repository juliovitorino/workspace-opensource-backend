package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.dto.SessionStateDTO;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final GuardianRestClientConsumer guardianRestClientConsumer;
    private final UserRepository userRepository;

    public LoginServiceImpl(GuardianRestClientConsumer guardianRestClientConsumer, UserRepository userRepository) {
        this.guardianRestClientConsumer = guardianRestClientConsumer;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<String> execute(UUID processId, LoginRequest loginRequest) {
        log.info("execute :: processId = {}", processId);
        String token = guardianRestClientConsumer.login(loginRequest);

        ControllerGenericResponse<SessionStateDTO> sessionState = guardianRestClientConsumer.findSessionState(token);
        SessionStateDTO objectResponse = sessionState.getObjectResponse();

        User userRepositoryByGuardianIntegration = userRepository.findByGuardianIntegrationUUID(objectResponse.getIdUserUUID());
        userRepositoryByGuardianIntegration.setLastLogin(objectResponse.getDateCreated());
        userRepository.save(userRepositoryByGuardianIntegration);

        ControllerGenericResponse<String> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                        .mensagem("You are logged in!")
                        .msgcode("MSG-1442")
                .build());
        response.setObjectResponse(token);
        return response;
    }
}
