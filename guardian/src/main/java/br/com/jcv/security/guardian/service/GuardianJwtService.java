package br.com.jcv.security.guardian.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface GuardianJwtService {
    String createJwtToken(String tokenSessionId);
    Jws<Claims> parseJwt(String jwtToken);

}
