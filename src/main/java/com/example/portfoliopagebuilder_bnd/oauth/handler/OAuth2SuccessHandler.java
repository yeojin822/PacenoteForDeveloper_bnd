package com.example.portfoliopagebuilder_bnd.oauth.handler;

import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.oauth.dto.PrincipalDetails;
import com.example.portfoliopagebuilder_bnd.oauth.dto.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
       log.info("로그인 성공 핸들러가 작동중입니다.");
        HttpSession session = request.getSession();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        session.setAttribute("USER", principalDetails);

        log.info("authentication : " + principalDetails.getUser());
        log.info("로그인 성공 ! 정보가져와서 토근 만들기 : : : {}", session);

        writeTokenResponse(response, session);
    }


    private void writeTokenResponse(HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(session));
        writer.flush();
    }


}
