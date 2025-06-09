package br.com.jcv.treinadorpro.corebusiness.trainingpack;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.PageResultRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.PageResultResponse;

import java.util.List;
import java.util.UUID;

public interface FindAllTrainingPackService extends
        BusinessService<PageResultRequest<UUID>, ControllerGenericResponse<PageResultResponse<TrainingPackResponse>>> {
}
