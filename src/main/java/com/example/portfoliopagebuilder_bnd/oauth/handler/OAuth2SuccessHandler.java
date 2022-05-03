package com.example.portfoliopagebuilder_bnd.oauth.handler;

import com.example.portfoliopagebuilder_bnd.oauth.dto.Token;
import com.example.portfoliopagebuilder_bnd.oauth.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       log.info("로그인 성공 핸들러가 작동중입니다.");
        OAuth2User oAuth2UserObj = (OAuth2User) authentication.getPrincipal();
        log.info("authentication : " + oAuth2UserObj.getAttributes());




//
 //       log.info("userDTO : : : {}", userDto);
//        Token token = tokenService.generateToken(userDto.getEmail(), "USER");
//        log.info("로그인 성공 ! 정보가져와서 토근 만들기 : : : {}", token);
//
//        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
