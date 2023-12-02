package br.com.jcv.security.guardian.controller.v1.business.groupuser;

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
@RequestMapping("/v1/api/business/groupuser")
public class CreateGroupUserRelationshipController {

    @Autowired private CreateGroupUserRelationshipBusinessService service;

    @PostMapping
    public ResponseEntity createGroupUserRelationship(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestBody @Valid CreateGroupUserRequest request) {
        return ResponseEntity.ok(service.execute(UUID.randomUUID(), jwtToken, request));
    }

}
