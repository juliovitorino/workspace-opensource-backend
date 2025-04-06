package br.com.jcv.security.guardian.controller.v1.business.heimdall;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/heimdall")
public class AskHeimdallPermissionController {
    @Autowired private AskHeimdallPermissionBusinessService service;
    @PostMapping(params = "role")
    public ResponseEntity<ControllerGenericResponse> askHeimdallHandler(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestParam @Valid String role) {
        return ResponseEntity.ok(service.execute(UUID.randomUUID(), jwtToken, role));
    }
}
