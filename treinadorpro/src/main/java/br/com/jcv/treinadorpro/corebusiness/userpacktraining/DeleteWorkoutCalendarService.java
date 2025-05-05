package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;

import java.util.List;
import java.util.UUID;

public interface DeleteWorkoutCalendarService extends BusinessService<List<UUID>, ControllerGenericResponse<Boolean>> {
}
