package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.CreateWorkoutCalendarServiceRequest;

public interface CreateWorkoutCalendarFromProgramTemplateService extends BusinessService<CreateWorkoutCalendarServiceRequest, ControllerGenericResponse<Boolean>> {
}
