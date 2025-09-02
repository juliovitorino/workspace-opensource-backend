package br.com.jcv.treinadorpro.corebusiness.goal;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;

import java.util.List;

public interface FindAllGoalService extends BusinessService<Boolean, ControllerGenericResponse<List<GoalResponse>>> {
}
