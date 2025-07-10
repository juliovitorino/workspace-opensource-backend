package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.dto.SessionStateDTO;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.interceptor.RequestTokenHolder;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
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
    public PersonalTrainerResponse execute(UUID processId) {
        ControllerGenericResponse<SessionStateDTO> sessionState = guardianRestClientConsumer.findSessionState(RequestTokenHolder.getToken());

        ControllerGenericResponse<PersonalTrainerResponse> personalTrainer = findPersonalTrainerByGuardianIdService
                .execute(UUID.randomUUID(), sessionState.getObjectResponse().getIdUserUUID());
        return personalTrainer.getObjectResponse();
    }

}
