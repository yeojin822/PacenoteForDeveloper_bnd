package com.example.portfoliopagebuilder_bnd.common.util;

import com.example.portfoliopagebuilder_bnd.oauth.dto.Token;
import com.example.portfoliopagebuilder_bnd.oauth.exception.ExceptionEnum;
import com.example.portfoliopagebuilder_bnd.oauth.exception.OAuth2ProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private String secretKey = "token-secret-key";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token generateToken(String uid, String role) {
        long tokenPeriod = 1000L * 60L * 10L;
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", role);

        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
    }

    public boolean verifyToken(String token){
        log.info("token check start !!");
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            log.info("verify Token ::: {}",claims.getBody().getExpiration().after(new Date()));

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (Exception e) {
            log.info("errorType ::: {}", e.getClass().getSimpleName());
            log.info("error ::: {}",e.getMessage());
            throw new OAuth2ProcessingException(ExceptionEnum.valueOf(e.getClass().getSimpleName()).getMsg());
        }
    }

    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
