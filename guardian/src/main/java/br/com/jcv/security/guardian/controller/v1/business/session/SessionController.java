package br.com.jcv.security.guardian.controller.v1.business.session;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/session")
public class SessionController {

    private final FindSessionByTokenService findSessionByTokenService;

    public SessionController(FindSessionByTokenService findSessionByTokenService) {
        this.findSessionByTokenService = findSessionByTokenService;
    }

    @GetMapping
    public ResponseEntity<ControllerGenericResponse<SessionStateDTO>> findSessionState(@RequestParam(value = "token") String token) {
        return ResponseEntity.ok(findSessionByTokenService.execute(UUID.randomUUID(),token));
    }

}
