package br.com.jcv.treinadorpro.infrastructure.interceptor;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestTokenHolder {

    private static final String TOKEN_KEY = "REQUEST_TOKEN";

    public static void setToken(String token) {
        RequestContextHolder.currentRequestAttributes()
            .setAttribute(TOKEN_KEY, token, RequestAttributes.SCOPE_REQUEST);
    }

    public static String getToken() {
        Object token = RequestContextHolder.currentRequestAttributes()
            .getAttribute(TOKEN_KEY, RequestAttributes.SCOPE_REQUEST);
        return token != null ? token.toString() : null;
    }
}
