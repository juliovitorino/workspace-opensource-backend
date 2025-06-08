package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.PersonalFeature;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalFeatureResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper.toResponse;

@Service
public class FindPersonalTrainerServiceImpl implements FindPersonalTrainerService {

    private final UserRepository userRepository;

    public FindPersonalTrainerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ControllerGenericResponse<PersonalTrainerResponse> execute(UUID processId, UUID uuid) {
        User user = userRepository.findByUuidId(uuid)
                .orElseThrow(
                        () -> new CommoditieBaseException("Invalid external id.", HttpStatus.BAD_REQUEST, "MSG-1247")
                );

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1254",
                "Personal Trainer Data retrieved successfully",
                toResponse(user)
        );

    }
}
