package com.devilpanda.todoapp.security.jwt;

import com.devilpanda.todoapp.security.UserPrinciple;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
    private static final String JWT_SIGN_SECRET = RandomStringUtils.random(32, true, true);

    public String generateToken(Authentication login) {
        UserPrinciple userPrinciple = (UserPrinciple) login.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, JWT_SIGN_SECRET)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SIGN_SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SIGN_SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.warn("Expired Jwt Token -> Message: {}", e.getMessage());
        } catch (SignatureException e) {
            LOGGER.warn("Invalid Jwt signature -> Message: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("Exception Jwt Token -> Message: {}", e.getMessage());
        }
        return false;
    }
}
