package com.competa.competademo.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Oleg Karimov
 */
public class RestApiException extends RuntimeException {
    private final HttpStatus httpStatus;

    public RestApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus=httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
