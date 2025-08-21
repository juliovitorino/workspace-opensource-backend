package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.FindMostRecentTrainingSessionRequest;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;

public interface FindTodayBookingService extends BusinessService<FindMostRecentTrainingSessionRequest, ControllerGenericResponse<TrainingSessionRequest>> {
}
