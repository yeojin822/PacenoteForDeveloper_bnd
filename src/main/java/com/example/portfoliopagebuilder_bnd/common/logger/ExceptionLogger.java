package com.example.portfoliopagebuilder_bnd.common.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Slf4j
@Aspect
@Component
public class ExceptionLogger {

    // com.aop.controller 이하 패키지의 모든 클래스 이하 모든 메서드에 적용
    @Pointcut("execution(* com.example.portfoliopagebuilder_bnd.*.controller.*(..))")
    private void exceptionLogging() {
    }

    @Around("exceptionLogging()")
    public void around(ProceedingJoinPoint pjp) {
        log.info("AOP Start");
        try {
            pjp.proceed();
        } catch (Exception e) {
            log.error("AOP Error Line");
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
