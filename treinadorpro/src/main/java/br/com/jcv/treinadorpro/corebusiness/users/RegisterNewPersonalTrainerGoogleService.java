package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.infrastructure.decoder.IPayloadLoginSocial;

public interface RegisterNewPersonalTrainerGoogleService extends BusinessService<IPayloadLoginSocial, ControllerGenericResponse<String>> {
}
