package com.example.portfoliopagebuilder_bnd.common.exception;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(value = {IllegalArgumentException.class})
//    protected ResponseEntity<ErrorResponse> handleDataException() {
//        log.error("handleDataException throw Exception : {}", BAD_REQUEST);
//        return ErrorResponse.toResponseEntity(BAD_REQUEST);
//    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Illegal arguments")
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentHandler() {
        log.error("handleDataException throw Exception : {}", BAD_REQUEST);
        //
    }
}
