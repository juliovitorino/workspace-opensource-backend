package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessServiceNoInput;
import br.com.jcv.treinadorpro.corelayer.response.TrainerAvailableTimeResponse;

public interface FindTrainerAvailableTimeService extends BusinessServiceNoInput<ControllerGenericResponse<TrainerAvailableTimeResponse>> {
}
