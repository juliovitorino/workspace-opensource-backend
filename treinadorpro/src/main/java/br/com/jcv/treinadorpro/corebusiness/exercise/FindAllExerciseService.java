package br.com.jcv.treinadorpro.corebusiness.exercise;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.ExerciseResponse;

import java.util.List;

public interface FindAllExerciseService extends BusinessService<Boolean, ControllerGenericResponse<List<ExerciseResponse>>> {
}
