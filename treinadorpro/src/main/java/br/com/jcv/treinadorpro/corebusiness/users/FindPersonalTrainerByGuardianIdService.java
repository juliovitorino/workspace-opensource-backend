package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;

import java.util.UUID;

public interface FindPersonalTrainerByGuardianIdService extends BusinessService<UUID, ControllerGenericResponse<PersonalTrainerResponse>> {
}
