package com.compassouol.productms.config.errorhandler.advicecontroller;

import com.compassouol.productms.config.errorhandler.ExceptionHandlerD;
import com.compassouol.productms.config.errorhandler.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodArgumentNotValidExceptionHandlerController {

    @Autowired
    private ExceptionHandlerD exceptionHandlerD;
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handle(MethodArgumentNotValidException exception) {
        return exceptionHandlerD
                .handle(HttpStatus.BAD_REQUEST, parseErrors(exception));
    }

    private String parseErrors(MethodArgumentNotValidException exception) {
        return parseFieldErrors(exception)
                .concat(parseObjectErrors(exception));
    }

    private String parseFieldErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors()
                .stream()
                .map(this::parseFieldError)
                .collect(Collectors.joining("; "));
    }

    private String parseFieldError(FieldError fieldError) {
        return String.format(
                "Field %s error: %s",
                fieldError.getField(),
                messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())
        );
    }

    private String parseObjectErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getGlobalErrors()
                .stream()
                .map(this::parseObjectError)
                .collect(Collectors.joining("; "));
    }

    private String parseObjectError(ObjectError objectError) {
        return String.format(
                "Object %s error: %s",
                objectError.getObjectName(),
                messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
        );
    }
}
