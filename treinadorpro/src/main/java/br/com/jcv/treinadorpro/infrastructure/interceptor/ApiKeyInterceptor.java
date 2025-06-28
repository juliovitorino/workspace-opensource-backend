package br.com.jcv.treinadorpro.infrastructure.interceptor;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.treinadorpro.infrastructure.helper.TreinadorProHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
@Slf4j
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final TreinadorProHelper treinadorProHelper;

    public ApiKeyInterceptor(TreinadorProHelper treinadorProHelper) {
        this.treinadorProHelper = treinadorProHelper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null) {
            ErrorInterceptorResponse error = new ErrorInterceptorResponse("X-API-KEY is null", 401);
            log.warn("X-API-KEY is null");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));

            return false;
        }

        try {
            treinadorProHelper.checkApiKey(UUID.fromString(apiKey));
            log.info("Your Api Key is valid! _o/");
        } catch (CommoditieBaseException e) {
            ErrorInterceptorResponse error = new ErrorInterceptorResponse("Invalid X-API-KEY", 401);
            log.warn("Invalid X-API-KEY");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));
            return false;
        }

        return true; // Api Key it's OK, go to endpoint
    }

}
