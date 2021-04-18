package com.compassouol.productms.config.errorhandler.advicecontroller;

import com.compassouol.productms.config.errorhandler.ExceptionHandlerD;
import com.compassouol.productms.config.errorhandler.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseStatusExceptionHandlerController {

    @Autowired
    private ExceptionHandlerD exceptionHandlerD;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handle(ResponseStatusException exception) {
        return exceptionHandlerD
                .handle(exception.getStatus(), exception.getReason());
    }
}
