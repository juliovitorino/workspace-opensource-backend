package br.com.jcv.security.guardian.service;

public interface GuardianJwtService {
    String createJwtToken(String tokenSessionId);

}
