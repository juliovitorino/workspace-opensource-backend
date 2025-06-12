package br.com.jcv.treinadorpro.corebusiness;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public abstract class AbstractTreinadorProService {

    private final UserRepository userRepository;
    private final TrainingPackRepository trainingPackRepository;

    protected AbstractTreinadorProService(UserRepository userRepository, TrainingPackRepository trainingPackRepository) {
        this.userRepository = userRepository;
        this.trainingPackRepository = trainingPackRepository;
    }

    protected void checkExistingEmail(RegisterRequest registerRequest){
        Optional<User> userByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (userByEmail.isPresent()) {
            throw new CommoditieBaseException("Email has already exist!", HttpStatus.BAD_REQUEST,"MSG-1922");
        }
    }

    protected void checkUserUUID(UUID userUUID){
        getUserByUUID(userUUID);
    }

    protected User checkActivePersonalTrainerUUID(UUID userUUID){
        User personalUser = getUser(userUUID);
        checkUserProfile(personalUser, UserProfileEnum.PERSONAL_TRAINER,"User is not a Personal Trainer");
        checkUserStatus(personalUser, StatusEnum.A, "Invalid user status");

        return personalUser;
    }

    protected User checkActiveStudentUUID(UUID userUUID){
        User user = getUser(userUUID);
        checkUserProfile(user, UserProfileEnum.STUDENT,"User is not a Student" );
        checkUserStatus(user, StatusEnum.A, "Invalid User Status");

        return user;
    }

    private void checkUserProfile(User user, UserProfileEnum profile, String errorMessage){
        if (user.getUserProfile() != profile) {
            throw new CommoditieBaseException(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1656");
        }
    }


    private void checkUserStatus(User user, StatusEnum status, String errorMessage){
        if (user.getStatus() != status) {
            throw new CommoditieBaseException(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1656");
        }
    }


    private User getUser(UUID userUUID) {
        User user = userRepository.findByUuidId(userUUID)
                .orElseThrow(() -> new CommoditieBaseException("Invalid User UUID", HttpStatus.BAD_REQUEST, "MSG-1203"));
        return user;
    }

    protected User getUserByUUID(UUID userUUID) {
        return userRepository.findByUuidId(userUUID)
                .orElseThrow(() -> new CommoditieBaseException("Invalid User UUID", HttpStatus.BAD_REQUEST, "MSG-1203"));
    }

    protected TrainingPack checkTrainingPackByExternalIdAndPersonalUserId(UUID externalId, Long personalUserId) {
        TrainingPack trainingPack = trainingPackRepository.findByExternalIdAndPersonalUserId(externalId, personalUserId)
                .orElseThrow(() -> new CommoditieBaseException(
                        "Invalid personal ID or Training pack ID",
                        HttpStatus.BAD_REQUEST,
                        "MSG-1631")
                );

        if ((!trainingPack.getStatus().equals("A"))) {
            throw new CommoditieBaseException("Invalid Training Pack Status", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1217");
        }
        return trainingPack;
    }
    protected TrainingPack findTrainingPackByExternalIdAndPersonalUserId(UUID externalId, Long personalUserId) {
        return trainingPackRepository.findByExternalIdAndPersonalUserId(externalId, personalUserId)
                .orElseThrow(() -> new CommoditieBaseException(
                        "Invalid personal ID or Training pack ID",
                        HttpStatus.BAD_REQUEST,
                        "MSG-1638")
                );

    }
}
