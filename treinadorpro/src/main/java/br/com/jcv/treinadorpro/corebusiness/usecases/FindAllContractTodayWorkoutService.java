package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessServiceNoInput;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;

import java.util.List;

public interface FindAllContractTodayWorkoutService extends BusinessServiceNoInput<ControllerGenericResponse<List<ContractResponse>>> {
}
