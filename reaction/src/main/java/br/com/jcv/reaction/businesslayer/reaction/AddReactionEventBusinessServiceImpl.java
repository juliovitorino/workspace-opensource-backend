package br.com.jcv.reaction.businesslayer.reaction;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.helper.MensagemResponseHelperService;
import br.com.jcv.reaction.adapter.controller.v1.business.reaction.ReactionEventRequest;
import br.com.jcv.reaction.corelayer.service.ReactionEventService;
import br.com.jcv.reaction.corelayer.service.ReactionService;
import br.com.jcv.reaction.infrastructure.dto.ReactionDTO;
import br.com.jcv.reaction.infrastructure.dto.ReactionEventDTO;
import br.com.jcv.reaction.infrastructure.exception.ReactionEventNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddReactionEventBusinessServiceImpl implements AddReactionEventBusinessService {
    @Autowired private ReactionEventService reactionEventService;
    @Autowired private ReactionService reactionService;
    @Autowired private GuardianRestClientConsumer guardianRestClientConsumer;

    @Override
    public MensagemResponse execute(UUID processId, ReactionEventRequest reactionRequest) {
        Boolean executed = guardianRestClientConsumer.validateApplicationCode(reactionRequest.getExternalAppUUID());
        if(executed.equals(Boolean.FALSE)) {
            return MensagemResponseHelperService.getInstance("RECT-0842", "Invalid Application Code.");
        }
        ReactionDTO reactionByNameAndStatus = reactionService.findReactionByNameAndStatus(reactionRequest.getReaction());

        ReactionEventDTO reactionEventDTO = this.toDto(reactionRequest, reactionByNameAndStatus);

        try {
            ReactionEventDTO md5check = reactionEventService.findReactionEventByHashMD5AndStatus(reactionEventDTO.getHashMD5(), GenericStatusEnums.ATIVO.getShortValue());
            reactionEventService.delete(md5check.getId());
            return MensagemResponseHelperService.getInstance("RECT-1317", "Reaction Event has been removed.");

        } catch (ReactionEventNotFoundException ignored) {
            log.info("execute :: No reaction event has been found. Skip.");
        }

        ReactionEventDTO saved = reactionEventService.salvar(reactionEventDTO);
        reactionEventService.updateStatusById(saved.getId(),GenericStatusEnums.ATIVO.getShortValue());

        return MensagemResponseHelperService.getInstance("RECT-0001", "Reaction Event has been included");
    }

    private ReactionEventDTO toDto(ReactionEventRequest reactionRequest, ReactionDTO reactionByNameAndStatus) {
        String seed = reactionByNameAndStatus.getId()
                + reactionRequest.getExternalAppUUID().toString()
                + reactionRequest.getExternalUserUUID().toString()
                + reactionRequest.getExternalItemUUID().toString();
        ReactionEventDTO dto = new ReactionEventDTO();
        dto.setReactionId(reactionByNameAndStatus.getId());
        dto.setExternalAppUUID(reactionRequest.getExternalAppUUID());
        dto.setExternalUserUUID(reactionRequest.getExternalUserUUID());
        dto.setExternalItemUUID(reactionRequest.getExternalItemUUID());
        dto.setHashMD5(DigestUtils.md5Hex(seed).toUpperCase());
        return dto;
    }


}
