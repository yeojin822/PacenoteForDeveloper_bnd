package com.example.portfoliopagebuilder_bnd.oauth.handler;

import com.example.portfoliopagebuilder_bnd.common.BaseResponse;
import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.oauth.dto.PrincipalDetails;
import com.example.portfoliopagebuilder_bnd.oauth.dto.Token;
import com.example.portfoliopagebuilder_bnd.oauth.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("Start oauth :: ");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("from user :: " + principalDetails.getUser());
        User user;
        // 세션키 생성
        try {
            Token token = jwtTokenProvider.createToken(principalDetails.getUser());
            log.info("token:: {}" , token);
            BaseResponse res = new BaseResponse();
            res.setBody(token);


            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            var writer = response.getWriter();
            writer.println(objectMapper.writeValueAsString(res));
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
