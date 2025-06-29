package br.com.jcv.treinadorpro.infrastructure.interceptor;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.dto.SessionStateDTO;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.treinadorpro.corebusiness.users.FindPersonalTrainerByGuardianIdService;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final GuardianRestClientConsumer guardianRestClientConsumer;
    private final FindPersonalTrainerByGuardianIdService findPersonalTrainerByGuardianIdService;

    public TokenInterceptor(@Lazy GuardianRestClientConsumer guardianRestClientConsumer,
                            FindPersonalTrainerByGuardianIdService findPersonalTrainerByGuardianIdService) {
        this.guardianRestClientConsumer = guardianRestClientConsumer;
        this.findPersonalTrainerByGuardianIdService = findPersonalTrainerByGuardianIdService;
    }

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

        ControllerGenericResponse<SessionStateDTO> sessionState = guardianRestClientConsumer.findSessionState(realToken);
        log.info("Requested Guardian Session State {}", sessionState.getObjectResponse());

        ControllerGenericResponse<PersonalTrainerResponse> personalTrainer = findPersonalTrainerByGuardianIdService
                .execute(UUID.randomUUID(), sessionState.getObjectResponse().getIdUserUUID());

        RequestTokenHolder.setToken(realToken);
        RequestTokenHolder.setPersonalTrainer(personalTrainer.getObjectResponse());

        log.info("Token has been moved to RequestTokenHolder");

        return true; // Token is valid, jump to endpoint
    }

}
