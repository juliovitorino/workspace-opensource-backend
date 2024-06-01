package br.com.jcv.restclient.guardian;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jcv.commons.library.http.HttpURLConnection;

@Component
public class GuardianRestClientConsumerImpl implements GuardianRestClientConsumer {

    static final String GUARDIAN_SERVICE_URL = "http://localhost:8080/v1/api/business";

    @Override
    public String login(@RequestBody LoginRequest request) {

        HttpURLConnection httpURLConnection = new HttpURLConnection();
        String jwtToken;
        try {
            jwtToken = httpURLConnection.sendPOST(GUARDIAN_SERVICE_URL + "/login", request, String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jwtToken;
    }

}
