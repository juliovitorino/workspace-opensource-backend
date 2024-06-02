package br.com.jcv.restclient.guardian;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jcv.commons.library.http.HttpURLConnection;
import br.com.jcv.restclient.exception.GuardianRestClientConsumerException;
import br.com.jcv.restclient.infrastructure.response.ControllerGenericResponse;

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

    @Override
    public ControllerGenericResponse askHeimdallPermission(String jwtToken, String role) {

        HttpURLConnection httpURLConnection = new HttpURLConnection();
        ControllerGenericResponse response = null;
        try {
            response = httpURLConnection.sendPOST(
                        GUARDIAN_SERVICE_URL + "/heimdall",
                        Map.of(HttpHeaders.AUTHORIZATION, jwtToken),
                        Map.of("role", role),
                        ControllerGenericResponse.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException("Guardian Microservice error", HttpStatus.BAD_GATEWAY);
        }
        return response;
    }

}
