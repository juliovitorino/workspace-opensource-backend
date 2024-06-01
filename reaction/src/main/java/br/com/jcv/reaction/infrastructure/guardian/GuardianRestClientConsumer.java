package br.com.jcv.reaction.infrastructure.guardian;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${guardian.openfeign-url}", value = "reaction-app")
public interface GuardianRestClientConsumer {

    @GetMapping("/v1/api/business/application/{uuidExternalApp}")
    ResponseEntity<Boolean> validateApplicationCode(@PathVariable("uuidExternalApp") UUID uuidExternalApp);
}
