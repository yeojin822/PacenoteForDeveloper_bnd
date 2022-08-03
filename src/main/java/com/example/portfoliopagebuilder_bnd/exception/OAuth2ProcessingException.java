package com.example.portfoliopagebuilder_bnd.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class OAuth2ProcessingException extends AuthenticationException {

    public OAuth2ProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2ProcessingException(String msg) {
        super(msg);
    }
}
