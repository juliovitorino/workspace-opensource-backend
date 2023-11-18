package br.com.jcv.security.guardian.service.impl;

import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.security.guardian.service.GuardianJwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class GuardianJwtServiceImpl implements GuardianJwtService {

    @Autowired private DateTime dateTime;

    @Override
    public String createJwtToken(String tokenSessionId) {
        final String seed = "LvqdFVwpliVGDyOH2imXAlrf5ni09doCEYv8myS8XkXORkAlCc";
        final long EXPIRATION_TIME = 2000;
        final Date now = dateTime.getToday();
        final Date expirationDate = DateUtility.getDate(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(tokenSessionId)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(dateTime.getToday())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,seed)
                .compact();
    }
}
