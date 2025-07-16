package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;

import java.util.UUID;

public interface FindContractService extends BusinessService<UUID, ControllerGenericResponse<ContractResponse>> {
}
