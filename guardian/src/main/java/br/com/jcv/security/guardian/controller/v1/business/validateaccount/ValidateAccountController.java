package br.com.jcv.security.guardian.controller.v1.business.validateaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/validate/account")
public class ValidateAccountController {

    @Autowired private ValidateAccountService validateAccountService;

    @PostMapping("/{externalAppUUID}")
    public ResponseEntity validateAccount(@PathVariable @Valid UUID externalAppUUID, @RequestBody @Valid ValidateAccountRequest request) {
        final UUID processId = UUID.randomUUID();
        request.setExternalUUID(externalAppUUID);
        return ResponseEntity.ok(validateAccountService.execute(processId, request));
    }

}
