package br.com.jcv.treinadorpro.corebusiness.workgroup;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.response.WorkGroupResponse;

import java.util.List;

public interface FindAllWorkGroupService extends BusinessService<Boolean, ControllerGenericResponse<List<WorkGroupResponse>>> {
}
