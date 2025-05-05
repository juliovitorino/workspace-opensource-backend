package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.EditPersonalTrainerProgramRequest;

@Deprecated
public interface EditPersonalTrainerProgramService extends BusinessService<EditPersonalTrainerProgramRequest, ControllerGenericResponse<Boolean>> {
}
