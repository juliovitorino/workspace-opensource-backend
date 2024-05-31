package br.com.jcv.reaction.businesslayer.reaction;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.reaction.adapter.controller.v1.business.reaction.ReactionEventRequest;
import br.com.jcv.reaction.adapter.controller.v1.business.reaction.ReactionRequest;

public interface AddReactionEventBusinessService extends BusinessService<ReactionEventRequest, MensagemResponse> {
}
