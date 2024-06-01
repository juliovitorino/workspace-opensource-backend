package br.com.jcv.security.guardian.controller.v1.business.rolegroup;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;

@RestController
@RequestMapping("/v1/api/business/batchrolegroup")
public class CreateBatchRoleGroupController {

    @Autowired private CreateBatchRoleGroupBusinessService createBatchRoleGroupBusinessService;

    @PostMapping
    public ResponseEntity<MensagemResponse> createBatchRoleGroup(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestBody @Valid CreateBatchRoleGroupRequest request) {
        return ResponseEntity.ok(createBatchRoleGroupBusinessService.execute(UUID.randomUUID(), jwtToken, request));

    }
}
