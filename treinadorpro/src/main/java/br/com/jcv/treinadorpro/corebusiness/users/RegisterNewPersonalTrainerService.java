package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;

public interface RegisterNewPersonalTrainerService extends BusinessService<RegisterRequest, ControllerGenericResponse<UserDTO>> {
}
