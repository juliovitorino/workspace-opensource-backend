package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.UserDataSheetPlanRequest;

public interface SaveUserWorkoutDataSheetPlanService extends BusinessService<UserDataSheetPlanRequest, ControllerGenericResponse<Boolean>> {
}
