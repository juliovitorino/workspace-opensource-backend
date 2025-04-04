package br.com.jcv.security.guardian.controller.v1.business.session;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.security.guardian.dto.SessionStateDTO;

public interface FindSessionByTokenService extends BusinessService<String, ControllerGenericResponse<SessionStateDTO>> {
}
