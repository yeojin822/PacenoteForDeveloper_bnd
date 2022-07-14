package com.example.portfoliopagebuilder_bnd.common.util;

import com.example.portfoliopagebuilder_bnd.oauth.dto.PrincipalDetails;
import com.example.portfoliopagebuilder_bnd.oauth.dto.Token;
import com.example.portfoliopagebuilder_bnd.oauth.exception.ExceptionEnum;
import com.example.portfoliopagebuilder_bnd.oauth.exception.OAuth2ProcessingException;
import com.example.portfoliopagebuilder_bnd.oauth.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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

    public Token createToken(User user) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User ppbUser = new User();
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

    public User verifyToken(String token){
        log.info("token check start !!");
        try {
            Claims  claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            ObjectMapper mapper = new ObjectMapper();
            User user =  mapper.readValue(claims.getSubject(), User.class);

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
