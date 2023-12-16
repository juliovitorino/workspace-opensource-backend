package br.com.jcv.security.guardian.controller.v1.business.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/login")
public class LoginController {

    @Autowired private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        final UUID processId = UUID.randomUUID();
        return ResponseEntity.ok(loginService.execute(processId, request));
    }
}
