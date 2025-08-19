package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.ContractScheduleModifierRequest;

public interface ContractScheduleModifierService extends BusinessService<ContractScheduleModifierRequest, ControllerGenericResponse<Boolean>> {
}
