package br.com.jcv.security.guardian.controller.v1.business.createaccount;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;

import java.util.UUID;

public interface CreateNewAccount extends BusinessService<CreateNewAccountRequest, ControllerGenericResponse<CreateNewAccountResponse>> {
}
