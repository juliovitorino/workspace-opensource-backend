package br.com.jcv.treinadorpro.infrastructure.utils;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;

public class ControllerGenericResponseHelper {

    public static <T> ControllerGenericResponse<T> getInstance(String msgcode, String message, T objectResponse) {
        ControllerGenericResponse<T> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                .msgcode(msgcode)
                .mensagem(message)
                .build());
        response.setObjectResponse(objectResponse);
        return response;
    }

    public static <T> ControllerGenericResponse<T> getInstance(String msgcode, String message) {
        return getInstance(msgcode, message, null);
    }
}
