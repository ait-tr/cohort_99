package com.competa.competademo.exceptions;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Konstantin Glazunov
 * created on 12-Jan-24 1
 */
public class IncorrectPasswordExeption extends RestApiException {
    public IncorrectPasswordExeption(final String template, Object... replacemens) {
        super(BAD_REQUEST, arrayFormat(template, replacemens).getMessage());
    }
}
