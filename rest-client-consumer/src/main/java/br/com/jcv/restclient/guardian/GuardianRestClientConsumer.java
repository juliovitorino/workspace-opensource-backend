package br.com.jcv.restclient.guardian;

import org.springframework.web.bind.annotation.RequestBody;

import br.com.jcv.restclient.infrastructure.response.ControllerGenericResponse;

public interface GuardianRestClientConsumer {
    String login(@RequestBody LoginRequest request) ;
    ControllerGenericResponse askHeimdallPermission(String jwtToken,String role);
}
