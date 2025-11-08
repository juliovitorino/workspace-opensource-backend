package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.enums.LoginSocialProviderEnum;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.LoginSocialRequest;
import br.com.jcv.treinadorpro.infrastructure.decoder.IPayloadLoginSocial;
import br.com.jcv.treinadorpro.infrastructure.decoder.JwtDecoder;
import br.com.jcv.treinadorpro.infrastructure.decoder.PayloadGoogleLoginSocial;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class LoginGoogleServiceImpl implements LoginGoogleService{

    private final UserRepository userRepository;
    private final RegisterNewPersonalTrainerGoogleService registerNewPersonalTrainerGoogleService;

    public LoginGoogleServiceImpl(UserRepository userRepository,
                                  RegisterNewPersonalTrainerGoogleService registerNewPersonalTrainerGoogleService) {
        this.userRepository = userRepository;
        this.registerNewPersonalTrainerGoogleService = registerNewPersonalTrainerGoogleService;
    }

    @Override
    public ControllerGenericResponse<String> execute(UUID processId, LoginSocialRequest loginSocialRequest) {

        IPayloadLoginSocial payloadGoogle = JwtDecoder.getPayload(loginSocialRequest.getToken(), PayloadGoogleLoginSocial.class);

        User user = userRepository.findByProviderAndIdGoogle(LoginSocialProviderEnum.google, payloadGoogle.getSub())
                .orElse(null);

        BusinessService<IPayloadLoginSocial, String> executor = Objects.isNull(user)
                ? (pid, payload) -> registerNewPersonalTrainerGoogleService.execute(pid,payload).getObjectResponse()
                : this::existentUser;

        String tokenGuardian = executor.execute(processId, payloadGoogle);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1448",
                "Your google login has been executed",
                tokenGuardian
        );
    }

    private String existentUser(UUID processId, IPayloadLoginSocial payloadGoogle) {
        return "token plataforma";
    }
}
