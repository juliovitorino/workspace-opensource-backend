package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;

import java.util.List;

public interface FindAllMyStudentsService extends BusinessService<Long, ControllerGenericResponse<List<UserPackTrainingDTO>>> {
}
