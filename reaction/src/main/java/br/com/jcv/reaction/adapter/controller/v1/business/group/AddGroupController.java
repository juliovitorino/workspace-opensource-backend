package br.com.jcv.reaction.adapter.controller.v1.business.group;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.reaction.businesslayer.group.AddGroupBusinessService;

@RestController
@RequestMapping("/v1/api/business/group")
public class AddGroupController {

    @Autowired private AddGroupBusinessService addGroupBusinessService;

    @PostMapping
    public ResponseEntity<MensagemResponse> addGroup(@RequestBody GroupRequest request) {
        return ResponseEntity.ok(addGroupBusinessService.execute(UUID.randomUUID(), request));
    }
}
