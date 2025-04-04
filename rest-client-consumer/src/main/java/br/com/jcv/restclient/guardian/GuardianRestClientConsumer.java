package br.com.jcv.restclient.guardian;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "guardianClient", url = "${guardian.api.url}")
public interface GuardianRestClientConsumer {

    @PostMapping("/v1/api/business/create-account")
    ControllerGenericResponse<UUID> createNewAccount(@RequestBody CreateNewAccountRequest request);

    @GetMapping("/v1/api/business/application/{uuidExternalApp}")
    Boolean validateApplicationCode(@PathVariable("uuidExternalApp") UUID uuidExternalApp);

}
