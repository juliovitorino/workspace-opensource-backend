package br.com.jcv.security.guardian.controller.v1.business.createaccount;

import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;

import java.util.UUID;

public interface CreateNewAccount extends BusinessService<CreateNewAccountRequest, ControllerGenericResponse<UUID>> {
}
