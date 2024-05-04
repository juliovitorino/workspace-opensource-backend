package br.com.jcv.preferences.adapter.v1.business.userpreferences.create;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.preferences.corebusiness.userpreferences.create.CreateUserPreferencesBusinessService;

@RestController
@RequestMapping("/v1/api/business/user-preferences")
public class CreateUserPreferencesController {

    @Autowired private CreateUserPreferencesBusinessService createUserPreferencesBusinessService;

    @PostMapping
    public ResponseEntity<CreateUserPreferencesResponse> createUserPreferences(@RequestBody CreateUserPreferencesRequest request) {
        return ResponseEntity.ok(createUserPreferencesBusinessService.execute(UUID.randomUUID(), request));

    }
}
