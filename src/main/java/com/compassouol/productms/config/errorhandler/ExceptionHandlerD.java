package com.compassouol.productms.config.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlerD {

    public ResponseEntity<ExceptionResponse> handle(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionResponse(
                        status.value(),
                        message
                ));
    }
}
