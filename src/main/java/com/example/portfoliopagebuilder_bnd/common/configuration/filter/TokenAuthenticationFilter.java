package com.example.portfoliopagebuilder_bnd.common.configuration.filter;

import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.login.dto.PrincipalDetails;
import com.example.portfoliopagebuilder_bnd.login.dto.RoleType;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.example.portfoliopagebuilder_bnd.login.service.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

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
    private final PrincipalDetailsService principalDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        log.info("api path : {} ", request.getServletPath());
        log.info("sessionkey  : {}", request.getHeader("sessionkey"));
        log.info("request.getRemoteAddr() :: {}", request.getRemoteAddr());
        Object session_obj = request.getHeader("sessionkey");
        UserDetails userDetails = null;

        if (session_obj != null) {
            if (!"prd".equals(environment.getActiveProfiles()[0]) && devKey.equals(session_obj.toString())) {
                userDetails = new PrincipalDetails(User.builder().id("test").role(RoleType.USER.getCode()).build());
                log.info("테스트 호출 했을때 principal:: {}", userDetails);
            } else if (jwtTokenProvider.validate(session_obj.toString())) {
                userDetails = principalDetailsService.loadUserByUsername(jwtTokenProvider.getUid(session_obj.toString()));
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
