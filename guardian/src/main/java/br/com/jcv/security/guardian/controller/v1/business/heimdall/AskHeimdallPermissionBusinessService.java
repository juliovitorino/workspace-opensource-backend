package br.com.jcv.security.guardian.controller.v1.business.heimdall;

import br.com.jcv.commons.library.commodities.service.BusinessServiceAuth;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;

public interface AskHeimdallPermissionBusinessService extends BusinessServiceAuth<String, ControllerGenericResponse> {
}
