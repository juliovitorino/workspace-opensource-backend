package br.com.jcv.security.guardian.controller.v1.business.validateapplicationcode;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.security.guardian.exception.GApplicationNotFoundException;

@RestController
@RequestMapping("/v1/api/business/application")
public class ValidateApplicationCodeController {

    @Autowired
    private ValidateApplicationCodeBusinessService validateApplicationCodeBusinessService;

    @GetMapping("{uuidExternalApp}")
    public ResponseEntity<Boolean> validateApplicationCode(@PathVariable("uuidExternalApp") UUID uuidExternalApp) {
        Boolean executed;
        try {
            executed = validateApplicationCodeBusinessService.execute(UUID.randomUUID(), uuidExternalApp);
        } catch (GApplicationNotFoundException e) {
            executed = Boolean.FALSE;
        }
        return ResponseEntity.ok(executed);
    }

}
