package br.com.jcv.preferences.adapter.v1.business.systempreferences.create;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.preferences.corebusiness.systempreferences.create.CreateSystemPreferencesBusinessService;
import br.com.jcv.preferences.infrastructure.config.PreferencesConfig;
import br.com.jcv.preferences.infrastructure.restclient.guardian.GuardianRestClientConsumer;

@RestController
@RequestMapping("/v1/api/business/system-preferences")
public class CreateSystemPreferencesController {

    @Autowired private CreateSystemPreferencesBusinessService createSystemPreferencesBusinessService;
    @Autowired private PreferencesConfig config;

    @PostMapping
    public ResponseEntity<CreateSystemPreferencesResponse> createSystemPreferences(@RequestBody CreateSystemPreferencesRequest request) {
        request.setUuidExternalApp(config.getGuardianApplicationPreferencesUUID());
        return ResponseEntity.ok(createSystemPreferencesBusinessService.execute(UUID.randomUUID(), request));
    }
}