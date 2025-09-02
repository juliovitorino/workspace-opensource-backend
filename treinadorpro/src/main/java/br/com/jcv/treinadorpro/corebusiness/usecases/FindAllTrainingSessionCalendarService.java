package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.FindAllTrainingSessionCalendarRequest;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;

import java.util.List;

public interface FindAllTrainingSessionCalendarService extends
        BusinessService<FindAllTrainingSessionCalendarRequest, ControllerGenericResponse<List<TrainingSessionRequest>>> {
}
