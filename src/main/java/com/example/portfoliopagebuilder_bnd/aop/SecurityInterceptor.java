package com.example.portfoliopagebuilder_bnd.aop;

import com.example.portfoliopagebuilder_bnd.oauth.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    public SecurityInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("호출 1 ::: {}", request.getRequestURI());
        log.info("호출 2 ::: {}", request.getRequestURL());

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(request.getMethod().equals("OPTIONS")) { // preflight로 넘어온 options는 통과
            return true;
        }
        else {
            if(token != null && token.length() > 0) {
                log.info("==== token check ====");
                log.info("==== token check  ::: {}",  tokenService.verifyToken(token));
               return tokenService.verifyToken(token); // 토큰 유효성 검증

            } else { // 유효한 인증토큰이 아닐 경우
                throw new Exception("유효한 인증토큰이 존재하지 않습니다.");
            }
        }
        //  return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("Interceptor > postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        log.debug("Interceptor > afterCompletion" );
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}