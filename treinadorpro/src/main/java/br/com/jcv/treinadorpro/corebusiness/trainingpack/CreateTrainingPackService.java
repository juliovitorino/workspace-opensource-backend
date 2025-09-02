package br.com.jcv.treinadorpro.corebusiness.trainingpack;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.CreateTrainingPackRequest;

@Deprecated
public interface CreateTrainingPackService extends BusinessService<CreateTrainingPackRequest, ControllerGenericResponse<Boolean>> {
}
