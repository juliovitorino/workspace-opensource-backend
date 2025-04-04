package br.com.jcv.reaction.adapter.controller.v1.business.reaction;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.reaction.businesslayer.reaction.AddReactionEventBusinessService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/business/reactionevent")
@Slf4j
public class AddReactionEventController {

    @Autowired private AddReactionEventBusinessService addReactionEventBusinessService;
    @Autowired private GuardianRestClientConsumer guardianRestClientConsumer;

    @PostMapping
    public ResponseEntity<MensagemResponse> addReactionEvent(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestBody ReactionEventRequest request)  {

        guardianRestClientConsumer.askHeimdallPermission(jwtToken,"REACTION_ADD_EVENT");

        log.info("addReactionEvent :: Token = {}", jwtToken);
        return ResponseEntity.ok(addReactionEventBusinessService.execute(UUID.randomUUID(), request));
    }
}
