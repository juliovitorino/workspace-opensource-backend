package br.com.jcv.restclient.brcities;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.jcv.commons.library.http.HttpURLConnection;
import br.com.jcv.restclient.brcities.response.UfResponse;
import br.com.jcv.restclient.exception.GuardianRestClientConsumerException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BrazilianCitiesRestClientConsumerImpl implements BrazilianCitiesRestClientConsumer {

    public static final String BRCITIES_MICROSERVICE_ERROR = "BR Cities Microservice error";

    private final HttpURLConnection httpURLConnection;

    static final String BRCITIES_SERVICE_URL = "http://localhost:8087/v1/api/business/uf";
    @Override
    public List<UfResponse> listAllFederationUnit() {

        List<UfResponse> response = null;
        try {
            response = httpURLConnection.sendGET(
                    BRCITIES_SERVICE_URL + "/",
                    List.class);
        } catch (IOException e) {
            throw new GuardianRestClientConsumerException(BRCITIES_MICROSERVICE_ERROR, HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        return response;
    }

}
