package br.com.jcv.treinadorpro.corebusiness.trainingpack;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;

import java.util.List;
import java.util.UUID;

public interface FindAllTrainingPackService extends BusinessService<UUID, ControllerGenericResponse<List<TrainingPackResponse>>> {
}
