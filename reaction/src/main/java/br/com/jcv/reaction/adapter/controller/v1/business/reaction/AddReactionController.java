package br.com.jcv.reaction.adapter.controller.v1.business.reaction;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.reaction.adapter.controller.v1.business.group.GroupRequest;
import br.com.jcv.reaction.businesslayer.group.AddGroupBusinessService;
import br.com.jcv.reaction.businesslayer.reaction.AddReactionBusinessService;

@RestController
@RequestMapping("/v1/api/business/reaction")
public class AddReactionController {

    @Autowired private AddReactionBusinessService addReactionBusinessService;

    @PostMapping
    public ResponseEntity<MensagemResponse> addGroup(@RequestBody ReactionRequest request) {
        return ResponseEntity.ok(addReactionBusinessService.execute(UUID.randomUUID(), request));
    }
}
