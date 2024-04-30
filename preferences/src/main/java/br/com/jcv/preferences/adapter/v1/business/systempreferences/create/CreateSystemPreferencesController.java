package br.com.jcv.preferences.adapter.v1.business.systempreferences.create;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.preferences.adapter.v1.business.userpreferences.create.CreateUserPreferencesBusinessService;
import br.com.jcv.preferences.adapter.v1.business.userpreferences.create.CreateUserPreferencesRequest;
import br.com.jcv.preferences.adapter.v1.business.userpreferences.create.CreateUserPreferencesResponse;

@RestController
@RequestMapping("/v1/api/business/system-preferences")
public class CreateSystemPreferencesController {

    @Autowired private CreateSystemPreferencesBusinessService createSystemPreferencesBusinessService;

    @PostMapping
    public ResponseEntity<CreateSystemPreferencesResponse> createUserPreferences(@RequestBody CreateSystemPreferencesRequest request) {
        return ResponseEntity.ok(createSystemPreferencesBusinessService.execute(UUID.randomUUID(), request));

    }
}
