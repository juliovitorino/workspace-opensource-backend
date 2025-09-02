package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.FindLastLoadExerciseRequest;
import br.com.jcv.treinadorpro.corelayer.response.FindLastLoadExerciseResponse;

public interface FindLastLoadExerciseService extends BusinessService<FindLastLoadExerciseRequest, ControllerGenericResponse<FindLastLoadExerciseResponse>> {
}
