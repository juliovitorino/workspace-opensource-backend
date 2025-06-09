package br.com.jcv.treinadorpro.corebusiness.goal;

import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;

import java.util.List;

public interface FindAllGoalService extends BusinessService<Boolean, List<GoalResponse>> {
}
