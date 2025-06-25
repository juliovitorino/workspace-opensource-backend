package br.com.jcv.security.guardian.controller.v1.business.createaccount;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
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
@RequestMapping("v1/api/business/create-account")
public class CreateNewAccountController {

    @Autowired private CreateNewAccount createNewAccount;
    @PostMapping
    public ResponseEntity<ControllerGenericResponse<CreateNewAccountResponse>> createAccount(@RequestBody @Valid CreateNewAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createNewAccount.execute(UUID.randomUUID(), request));

    }
}
