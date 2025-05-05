package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.request.CreatePersonalTrainerProgramFromTemplateRequest;

@Deprecated
public interface CreatePersonalTrainerProgramFromTemplateService
        extends BusinessService<CreatePersonalTrainerProgramFromTemplateRequest, ControllerGenericResponse<Boolean>> {
}
