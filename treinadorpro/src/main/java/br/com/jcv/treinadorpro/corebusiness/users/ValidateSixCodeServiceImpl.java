package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.request.ValidateSixCodeRequest;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.infrastructure.config.TreinadorProConfig;
import br.com.jcv.treinadorpro.infrastructure.helper.TreinadorProHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidateSixCodeServiceImpl implements ValidateSixCodeService{

    private final GuardianRestClientConsumer guardianRestClientConsumer;
    private final UserRepository userRepository;
    private final TreinadorProHelper treinadorProHelper;


    public ValidateSixCodeServiceImpl(GuardianRestClientConsumer guardianRestClientConsumer,
                                      UserRepository userRepository,
                                      TrainingPackRepository trainingPackRepository,
                                      TreinadorProConfig config,
                                      TreinadorProHelper treinadorProHelper) {
        this.guardianRestClientConsumer = guardianRestClientConsumer;
        this.userRepository = userRepository;
        this.treinadorProHelper = treinadorProHelper;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, ValidateSixCodeRequest request) {
        User trainer = treinadorProHelper.checkPendingPersonalTrainerUUID(request.getExternalUserUUID());
        guardianRestClientConsumer.validateSixDigitCode(request.getExternalAppUUID(),trainer.getGuardianIntegrationUUID(),request);

        trainer.setStatus(StatusEnum.A);
        userRepository.save(trainer);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1242",
                "your account has been activated successfully",
                Boolean.TRUE
        );
    }
}
