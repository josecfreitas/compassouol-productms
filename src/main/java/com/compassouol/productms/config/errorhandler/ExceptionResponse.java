package com.compassouol.productms.config.errorhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    @JsonProperty("status_code")
    private final int statusCode;
    private final String message;
}
