package br.com.jcv.security.guardian.controller.v1.business.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/bootstrap")
public class InitialGuardianSetupController {
    @Autowired private InitialGuardianSetupBusinessService service;
    @GetMapping("{magic-seed}")
    public ResponseEntity guardianBootStrap(@PathVariable("magic-seed") String magicSeed) {
        final UUID processId = UUID.randomUUID();
        return ResponseEntity.ok(service.execute(processId, magicSeed));
    }
}
