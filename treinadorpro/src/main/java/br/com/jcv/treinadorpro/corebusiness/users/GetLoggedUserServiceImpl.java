package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.interceptor.RequestTokenHolder;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLoggedUserServiceImpl implements GetLoggedUserService{
    @Override
    public ControllerGenericResponse<PersonalTrainerResponse> execute(UUID processId) {
        try {
            PersonalTrainerResponse personalTrainer = RequestTokenHolder.getPersonalTrainer();
            return ControllerGenericResponseHelper.getInstance(
                    "MSG-1328",
                    "User logged has been retrieved successfully",
                    personalTrainer
            );
        } catch (JsonProcessingException e) {
            throw new CommoditieBaseException("MSG-1329", HttpStatus.UNPROCESSABLE_ENTITY, "It wasn't possible to recover logged user");
        }
    }
}
