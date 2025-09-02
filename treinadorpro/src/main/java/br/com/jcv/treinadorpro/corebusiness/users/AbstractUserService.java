package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Deprecated
public abstract class AbstractUserService {

    private final UserRepository userRepository;

    @Deprecated
    protected AbstractUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Deprecated
    protected void checkExistingEmail(RegisterRequest registerRequest){
        Optional<User> userByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (userByEmail.isPresent()) {
            throw new CommoditieBaseException("Email has already exist!", HttpStatus.BAD_REQUEST,"MSG-1922");
        }

    }

    @Deprecated
    protected void checkUserUUID(UUID userUUID){
        getUserByUUID(userUUID);
    }

    @Deprecated
    protected void checkPersonalTrainerUUID(UUID userUUID){
        User personalUser = userRepository.findByUuidId(userUUID)
                .orElseThrow(() -> new CommoditieBaseException("Invalid User UUID", HttpStatus.BAD_REQUEST, "MSG-1203"));

        if (personalUser.getUserProfile() != UserProfileEnum.PERSONAL_TRAINER) {
            throw new CommoditieBaseException("User is not a Personal Trainer", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1215");
        }

        if ((personalUser.getStatus() != StatusEnum.A)) {
            throw new CommoditieBaseException("Invalid User Status", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1217");
        }
    }

    @Deprecated
    protected User getUserByUUID(UUID userUUID) {
        return userRepository.findByUuidId(userUUID)
                .orElseThrow(() -> new CommoditieBaseException("Invalid User UUID", HttpStatus.BAD_REQUEST, "MSG-1203"));
    }
}
