package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.request.UserPackTrainingRequest;

public interface CreateUserPackTrainingService extends BusinessService<UserPackTrainingRequest, ControllerGenericResponse<UserPackTrainingDTO>> {
}
