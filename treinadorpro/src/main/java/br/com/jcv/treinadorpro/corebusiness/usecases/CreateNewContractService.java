package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.CreateNewStudentContractRequest;
import br.com.jcv.treinadorpro.corelayer.response.CreateNewStudentContractResponse;

public interface CreateNewContractService
        extends BusinessService<CreateNewStudentContractRequest, ControllerGenericResponse<CreateNewStudentContractResponse>> {
}
