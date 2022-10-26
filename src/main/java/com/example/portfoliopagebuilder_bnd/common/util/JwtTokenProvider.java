package com.example.portfoliopagebuilder_bnd.common.util;

import com.example.portfoliopagebuilder_bnd.login.dto.Token;
import com.example.portfoliopagebuilder_bnd.login.exception.ExceptionEnum;
import com.example.portfoliopagebuilder_bnd.login.exception.OAuth2ProcessingException;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${token.secretKey}")
    protected String secretKey;

    @Value("${token.tokenPeriod}")
    protected long tokenPeriod;

    private static final String AUTHORITIES_KEY = "role";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token createToken(User user) throws Exception {
        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setSubject(user.getId())
                        .claim(AUTHORITIES_KEY, user.getRole())
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
    }


    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        return this.getTokenClaims(token) != null;
    }

    public Claims getTokenClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("errorType ::: {}", e.getClass().getSimpleName());
            log.info("error ::: {}",e.getMessage());
            throw new OAuth2ProcessingException(ExceptionEnum.valueOf(e.getClass().getSimpleName()).getMsg());
        }
    }
}
