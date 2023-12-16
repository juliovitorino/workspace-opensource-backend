package br.com.jcv.security.guardian.service.impl;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.security.guardian.service.GuardianJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class GuardianJwtServiceImpl implements GuardianJwtService {

    private static final String SEED = "LvqdFVwpliVGDyOH2imXAlrf5ni09doCEYv8myS8XkXORkAlCc";

    @Autowired private DateTime dateTime;

    @Override
    public String createJwtToken(String tokenSessionId, Long ttl) {
        final Date now = dateTime.getToday();
        final Date expirationDate = DateUtility.getDate(now.getTime() + ttl);

        return Jwts.builder()
                .setSubject(tokenSessionId)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(dateTime.getToday())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,SEED)
                .compact();
    }

    @Override
    public Jws<Claims> parseJwt(String jwtToken) {
        Key hKey = new SecretKeySpec(Base64.getDecoder().decode(SEED), SignatureAlgorithm.HS256.getJcaName());
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parserBuilder().setSigningKey(hKey).build().parseClaimsJws(jwtToken);
        } catch (ExpiredJwtException e){
            throw new CommoditieBaseException("Your token has expired.", HttpStatus.FORBIDDEN);
        } catch (MalformedJwtException e) {
            throw new CommoditieBaseException("Invalid JWT token format. Please check it out your Authorization Header", HttpStatus.FORBIDDEN);
        }
        return claimsJws;
    }
}
