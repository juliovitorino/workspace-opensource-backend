package br.com.jcv.brcities.businesslayer.uf;

import java.util.List;

import br.com.jcv.brcities.adapter.controller.v1.business.uf.UfResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;

public interface ListAllFederationUnitBusinessService extends BusinessService<Boolean, List<UfResponse>> {
}
