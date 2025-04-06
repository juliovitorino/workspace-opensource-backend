package br.com.jcv.restclient.guardian;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.dto.SessionStateDTO;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "guardianClient", url = "${guardian.api.url}")
public interface GuardianRestClientConsumer {

    @PostMapping("/v1/api/business/create-account")
    ControllerGenericResponse<UUID> createNewAccount(@RequestBody CreateNewAccountRequest request);

    @GetMapping("/v1/api/business/application/{uuidExternalApp}")
    Boolean validateApplicationCode(@PathVariable("uuidExternalApp") UUID uuidExternalApp);

    @PostMapping("/v1/api/business/login")
    String login(@RequestBody LoginRequest request);

    @GetMapping("/v1/api/business/session")
    ControllerGenericResponse<SessionStateDTO> findSessionState(@RequestParam(value = "token") String token);

}
