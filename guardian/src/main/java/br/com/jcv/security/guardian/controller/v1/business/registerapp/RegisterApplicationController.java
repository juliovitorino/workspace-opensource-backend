package br.com.jcv.security.guardian.controller.v1.business.registerapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/register-app")
public class RegisterApplicationController {

    @Autowired private RegisterApplication registerApplication;

    @PostMapping
    public ResponseEntity registerApp(@RequestBody @Valid RegisterApplicationRequest request) {
        UUID processId = UUID.randomUUID();
        return ResponseEntity.status(HttpStatus.CREATED).body(registerApplication.execute(processId, request));
    }
}
