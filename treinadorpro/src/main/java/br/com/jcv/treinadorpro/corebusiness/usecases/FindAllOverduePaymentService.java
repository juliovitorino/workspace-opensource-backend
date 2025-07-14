package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessServiceNoInput;
import br.com.jcv.treinadorpro.corelayer.response.StudentPaymentResponse;

import java.util.List;

public interface FindAllOverduePaymentService extends BusinessServiceNoInput<ControllerGenericResponse<List<StudentPaymentResponse>>> {
}
