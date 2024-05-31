package br.com.jcv.reaction.businesslayer.reaction;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.helper.MensagemResponseHelperService;
import br.com.jcv.reaction.adapter.controller.v1.business.reaction.ReactionRequest;
import br.com.jcv.reaction.corelayer.service.ReactionService;
import br.com.jcv.reaction.infrastructure.dto.ReactionDTO;
import br.com.jcv.reaction.infrastructure.exception.GroupNotFoundException;
import br.com.jcv.reaction.infrastructure.exception.ReactionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddReactionBusinessServiceImpl implements AddReactionBusinessService {

    private final ReactionService reactionService;

    @Override
    public MensagemResponse execute(UUID processId, ReactionRequest reactionRequest) {

        try{
            reactionService.findReactionByNameAndStatus(reactionRequest.getName().toLowerCase(), GenericStatusEnums.ATIVO.getShortValue());
            throw new CommoditieBaseException("Reaction has been found.", HttpStatus.BAD_REQUEST, "ERR-1206");
        } catch (ReactionNotFoundException ignored) {
            log.info("execute :: No active reaction has been found... keep going.");
        }

        ReactionDTO saved = reactionService.salvar(this.toDto(reactionRequest));
        reactionService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return MensagemResponseHelperService.getInstance("RECT-0001", "Reaction has been included");
    }

    private ReactionDTO toDto(ReactionRequest request) {
        ReactionDTO dto = new ReactionDTO();
        dto.setName(request.getName().toLowerCase());
        dto.setIcon(request.getIcon());
        dto.setTag(request.getTag());
        return dto;
    }
}
