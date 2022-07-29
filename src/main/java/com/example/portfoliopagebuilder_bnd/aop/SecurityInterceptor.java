package com.example.portfoliopagebuilder_bnd.aop;

import com.example.portfoliopagebuilder_bnd.common.util.AuthorizationExtractor;
import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.common.util.PpbUtill;
import com.example.portfoliopagebuilder_bnd.oauth.dto.PpbUser;
import com.example.portfoliopagebuilder_bnd.oauth.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;
    private AuthorizationExtractor authorizationExtractor;

    @Value("${token.devKey}")
    private String devKey;

    @Autowired
    private Environment environment;

    public SecurityInterceptor(AuthorizationExtractor authorizationExtractor, JwtTokenProvider jwtTokenProvider) {
        this.authorizationExtractor = authorizationExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("api path : " + request.getServletPath());
        log.info(request.getHeader("sessionkey"));
        log.info("request.getRemoteAddr() :: " + request.getRemoteAddr());
        Object session_obj = request.getHeader("sessionkey");

        // options 는 헤더가 없다.
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 세션이 없으면 에러
        if (session_obj == null) {
            log.info("session_obj == null");
            throw new Exception("유효한 인증토큰이 존재하지 않습니다.");
        }

        // 개발이면서 refine이면 통과
        if (
                (!"prd".equals(environment.getActiveProfiles()[0]) && devKey.equals(session_obj.toString()))
        ) {

            return true;
        }

        // 유저 검증
        try {
            PpbUser user = jwtTokenProvider.verifyToken(session_obj.toString());
            log.info("id : {} , name: {}", user.getId(), user.getUsername());
            request.setAttribute("User", user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("Interceptor > postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        log.debug("Interceptor > afterCompletion" );
    }

}
