package com.example.portfoliopagebuilder_bnd.aop;

import com.example.portfoliopagebuilder_bnd.common.util.AuthorizationExtractor;
import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;
    private AuthorizationExtractor authorizationExtractor;

    public SecurityInterceptor(AuthorizationExtractor authorizationExtractor, JwtTokenProvider jwtTokenProvider) {
        this.authorizationExtractor = authorizationExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("호출 1 ::: {}", request.getRequestURI());
        log.info("호출 2 ::: {}", request.getRequestURL());

        String token = authorizationExtractor.extract(request, "Bearer");

        if(request.getMethod().equals("OPTIONS")) { // preflight로 넘어온 options는 통과
            return true;
        }
        else {
            if(token != null && token.length() > 0) {
                log.info("==== token check ====");
                log.info("==== token ==== ::: {}", token);
               try {
                 return jwtTokenProvider.verifyToken(token); // 토큰 유효성 검증
               }catch (Exception e){
                   throw new Exception(e.getMessage());
               }
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

}
