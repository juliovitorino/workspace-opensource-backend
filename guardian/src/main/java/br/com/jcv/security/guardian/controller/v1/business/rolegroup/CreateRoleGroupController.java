package br.com.jcv.security.guardian.controller.v1.business.rolegroup;

import br.com.jcv.security.guardian.controller.v1.business.groupuser.CreateGroupUserRequest;
import org.apache.coyote.Response;
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
@RequestMapping("/v1/api/business/rolegroup")
public class CreateRoleGroupController {

    @Autowired private CreateRoleGroupBusinessService service; 
    @PostMapping
    public ResponseEntity createRoleGroupRelationship(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestBody @Valid CreateRoleGroupRequest request) {
        return ResponseEntity.ok(service.execute(UUID.randomUUID(), jwtToken, request));

    }
}
