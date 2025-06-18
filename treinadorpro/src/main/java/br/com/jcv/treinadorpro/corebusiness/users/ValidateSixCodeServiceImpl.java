package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.request.ValidateSixCodeRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidateSixCodeServiceImpl implements ValidateSixCodeService{

    private final GuardianRestClientConsumer guardianRestClientConsumer;

    public ValidateSixCodeServiceImpl(GuardianRestClientConsumer guardianRestClientConsumer) {
        this.guardianRestClientConsumer = guardianRestClientConsumer;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, ValidateSixCodeRequest request) {
        ControllerGenericResponse<?> controllerGenericResponse = guardianRestClientConsumer.validateSixDigitCode(
                request.getExternalAppUUID(),
                request.getExternalUserUUID(),
                request);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1242",
                "your account has been activated successfully",
                Boolean.TRUE
        );
    }
}
