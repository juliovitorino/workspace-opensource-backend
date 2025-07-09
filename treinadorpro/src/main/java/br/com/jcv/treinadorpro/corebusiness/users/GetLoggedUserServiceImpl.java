package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.dto.SessionStateDTO;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.interceptor.RequestTokenHolder;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLoggedUserServiceImpl implements GetLoggedUserService {

    private final GuardianRestClientConsumer guardianRestClientConsumer;
    private final FindPersonalTrainerByGuardianIdService findPersonalTrainerByGuardianIdService;

    public GetLoggedUserServiceImpl(GuardianRestClientConsumer guardianRestClientConsumer,
                                    FindPersonalTrainerByGuardianIdService findPersonalTrainerByGuardianIdService) {
        this.guardianRestClientConsumer = guardianRestClientConsumer;
        this.findPersonalTrainerByGuardianIdService = findPersonalTrainerByGuardianIdService;
    }

    @Override
    public ControllerGenericResponse<PersonalTrainerResponse> execute(UUID processId) {
        PersonalTrainerResponse personalTrainer = getTrainerFromToken(RequestTokenHolder.getToken());
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1328",
                "User logged has been retrieved successfully",
                personalTrainer
        );

    }

    private PersonalTrainerResponse getTrainerFromToken(String token) {

        ControllerGenericResponse<SessionStateDTO> sessionState = guardianRestClientConsumer.findSessionState(token);

        ControllerGenericResponse<PersonalTrainerResponse> personalTrainer = findPersonalTrainerByGuardianIdService
                .execute(UUID.randomUUID(), sessionState.getObjectResponse().getIdUserUUID());
        return personalTrainer.getObjectResponse();
    }


}
