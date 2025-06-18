package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.restclient.guardian.request.ValidateSixCodeRequest;

public interface ValidateSixCodeService extends BusinessService<ValidateSixCodeRequest, ControllerGenericResponse<Boolean>> {
}
