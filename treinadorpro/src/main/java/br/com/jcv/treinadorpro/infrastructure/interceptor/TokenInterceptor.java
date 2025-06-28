package br.com.jcv.treinadorpro.infrastructure.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            ErrorInterceptorResponse error = new ErrorInterceptorResponse("Token is null or not start with Bearer", 401);
            log.warn("Token is null or not start with Bearer");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));

            return false;
        }

        String realToken = token.substring(7);
        RequestTokenHolder.setToken(realToken);

        log.info("Token has been moved to RequestTokenHolder");

        return true; // Token is valid, jump to endpoint
    }

}
