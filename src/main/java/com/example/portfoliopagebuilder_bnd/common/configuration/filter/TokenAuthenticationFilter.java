package com.example.portfoliopagebuilder_bnd.common.configuration.filter;

import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.core.env.Environment;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final String devKey;
    private final Environment environment;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        log.info("token.devkey  ::: {}", devKey);
        log.info("api path : " + request.getServletPath());
        log.info(request.getHeader("sessionkey"));
        log.info("request.getRemoteAddr() :: " + request.getRemoteAddr());
        Object session_obj = request.getHeader("sessionkey");
        log.info("test session_obj ::: {}", session_obj.toString());
        if (session_obj != null) {
            if (!"prd".equals(environment.getActiveProfiles()[0]) && devKey.equals(session_obj.toString())) {
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getTestAuthentication(session_obj.toString()));
            }
            else if(jwtTokenProvider.validate(session_obj.toString())) {
                Authentication authentication = jwtTokenProvider.getAuthentication(session_obj.toString());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
