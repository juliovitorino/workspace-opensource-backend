package br.com.jcv.treinadorpro.infrastructure.interceptor;

import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestTokenHolder {

    private static final String TOKEN_KEY = "REQUEST_TOKEN";
    private static final String TRAINER_KEY = "PERSONAL_TRAINER";

    public static void setToken(String token) {
        RequestContextHolder.currentRequestAttributes()
            .setAttribute(TOKEN_KEY, token, RequestAttributes.SCOPE_REQUEST);
    }

    public static String getToken() {
        Object token = RequestContextHolder.currentRequestAttributes()
            .getAttribute(TOKEN_KEY, RequestAttributes.SCOPE_REQUEST);
        return token != null ? token.toString() : null;
    }

    public static void setPersonalTrainer(PersonalTrainerResponse personalTrainer) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        RequestContextHolder.currentRequestAttributes()
            .setAttribute(TRAINER_KEY, objectMapper.writeValueAsString(personalTrainer), RequestAttributes.SCOPE_REQUEST);
    }

    public static PersonalTrainerResponse getPersonalTrainer() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Object personal = RequestContextHolder.currentRequestAttributes()
            .getAttribute(TRAINER_KEY, RequestAttributes.SCOPE_REQUEST);
        return personal != null ? objectMapper.readValue(personal.toString(), PersonalTrainerResponse.class) : null;
    }
}
