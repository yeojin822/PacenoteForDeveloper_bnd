package com.example.portfoliopagebuilder_bnd.common.util;

import com.example.portfoliopagebuilder_bnd.common.exception.TokenValidFailedException;
import com.example.portfoliopagebuilder_bnd.login.dto.PpbUser;
import com.example.portfoliopagebuilder_bnd.login.dto.RoleType;
import com.example.portfoliopagebuilder_bnd.login.dto.Token;
import com.example.portfoliopagebuilder_bnd.login.exception.ExceptionEnum;
import com.example.portfoliopagebuilder_bnd.login.exception.OAuth2ProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

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

    public Authentication getAuthentication(String token) {

        if(this.validate(token)) {
            Claims claims = this.getTokenClaims(token);
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            log.info("로그인 하고 authorities:: {}", authorities);
            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities);
            log.info("로그인 하고 :: {}", principal);
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }

    public Authentication getTestAuthentication(String token) {
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(new String[]{"ROLE_USER","ROLE_ADMIN"})
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        log.info("테스트 호출 했을때 authorities:: {}", authorities);
        User principal = new User("test", "", authorities);
        log.info("테스트 호출 했을때 principal:: {}", principal);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
