package com.example.portfoliopagebuilder_bnd.common.util;

import com.example.portfoliopagebuilder_bnd.login.dto.PpbUser;
import com.example.portfoliopagebuilder_bnd.login.dto.Token;
import com.example.portfoliopagebuilder_bnd.login.exception.ExceptionEnum;
import com.example.portfoliopagebuilder_bnd.login.exception.OAuth2ProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    private String secretKey;

    @Value("${token.tokenPeriod}")
    long tokenPeriod;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token createToken(PpbUser user) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PpbUser ppbUser = new PpbUser();
        ppbUser.setId(user.getId());
        ppbUser.setUsername(user.getUsername());

        String userStr = mapper.writeValueAsString(ppbUser);

        Claims claims = Jwts.claims().setSubject(userStr);

        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
    }

    public PpbUser verifyToken(String token){
        log.info("token check start !!");
        try {
            Claims  claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            ObjectMapper mapper = new ObjectMapper();
            PpbUser user =  mapper.readValue(claims.getSubject(), PpbUser.class);

            return user;

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
