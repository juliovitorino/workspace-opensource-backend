package br.com.jcv.treinadorpro.infrastructure.decoder;

import br.com.jcv.treinadorpro.corelayer.exception.InvalidTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JwtDecoder {

    public static String decodeBase64UrlSafe(String base64UrlSafe){
        byte[] decodedBytes = Base64.getUrlDecoder().decode(base64UrlSafe);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public static String getToken(String token){
        final String bearer = "Bearer ";
        if(!token.contains(bearer)) {
            throw new InvalidTokenException("Invalid Token Authorization", HttpStatus.FORBIDDEN);
        }
        return token.substring(bearer.length());
    }

    public static String getHeader(String token) {
        String idToken = getToken(token);
        String[] parts = idToken.split("\\.");
        return decodeBase64UrlSafe(parts[0]);
    }

    public static <T> IPayloadLoginSocial getPayload(String token, Class<T> payload){
        String idToken = getToken(token);
        String[] parts = idToken.split("\\.");
        String payloadStr = decodeBase64UrlSafe(parts[1]);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (IPayloadLoginSocial) objectMapper.readValue(payloadStr, payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

}
