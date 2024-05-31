package br.com.jcv.reaction.businesslayer.group;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.reaction.adapter.controller.v1.business.group.GroupRequest;

public interface AddGroupBusinessService extends BusinessService<GroupRequest, MensagemResponse> {
}
