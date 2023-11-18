package br.com.jcv.security.guardian.controller.v1.business.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.HandlesTypes;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/role")
public class CreateRoleController {

    @Autowired private CreateRoleService createRoleService;

    @PostMapping
    public ResponseEntity createRole(@RequestBody @Valid RoleRequest request, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken) {
        final UUID processId = UUID.randomUUID();
        return ResponseEntity.ok(createRoleService.execute(processId, jwtToken, request));

    }
}
