package br.com.jcv.restclient.guardian;

import org.springframework.web.bind.annotation.RequestBody;

public interface GuardianRestClientConsumer {
    String login(@RequestBody LoginRequest request) ;
}
