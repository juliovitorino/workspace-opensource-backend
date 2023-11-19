package br.com.jcv.security.guardian.controller.v1.business.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/group-user")
public class GroupUserRelationshipController {
    @Autowired private CreateGroupUserRelationshipBusinessService service;

    @PostMapping
    public ResponseEntity createGroupUserRelationship(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestBody @Valid GroupUserRequest request) {
        final UUID processId = UUID.randomUUID();
        return ResponseEntity.ok(service.execute(processId, jwtToken, request));

    }
}
