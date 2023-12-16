package br.com.jcv.security.guardian.controller.v1.business.group;

import br.com.jcv.commons.library.commodities.service.BusinessServiceAuth;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;

public interface CreateGroupBusinessService extends BusinessServiceAuth<GroupRequest, ControllerGenericResponse> {
}
