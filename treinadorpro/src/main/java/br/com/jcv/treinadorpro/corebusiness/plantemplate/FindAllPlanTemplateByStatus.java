package br.com.jcv.treinadorpro.corebusiness.plantemplate;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;

import java.util.List;

public interface FindAllPlanTemplateByStatus extends BusinessService<String, ControllerGenericResponse<List<PlanTemplateResponse>>> {
}
