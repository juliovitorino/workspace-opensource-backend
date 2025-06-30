package br.com.jcv.restclient.decoder;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.restclient.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class GuardianIntegrationErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException("Resource not found.");
        }
        if (response.status() >= 400 && response.status() < 500) {
            return extractMessage(response);
        }
        return defaultDecoder.decode(methodKey, response);
    }

    private CommoditieBaseException extractMessage(Response response) {
        try (InputStream body = response.body().asInputStream()) {
            String errorJson = new String(body.readAllBytes(), StandardCharsets.UTF_8);
            log.warn("Guardian Exception => {}",errorJson);
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorResponse errorResponse = objectMapper.readValue(errorJson, ErrorResponse.class);
            return new CommoditieBaseException(errorResponse.getMessage(), HttpStatus.resolve(errorResponse.getStatusCode()), errorResponse.getMsgcode());
        } catch (IOException e) {
            return new CommoditieBaseException("Unknown Error", HttpStatus.BAD_GATEWAY, "MSG-0941");
        }
    }

}
