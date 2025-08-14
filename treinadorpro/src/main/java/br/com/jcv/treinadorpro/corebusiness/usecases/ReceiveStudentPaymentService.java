package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.ReceiveStudentPaymentRequest;

public interface ReceiveStudentPaymentService extends BusinessService<ReceiveStudentPaymentRequest, ControllerGenericResponse<Boolean>> {
}
