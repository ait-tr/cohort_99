package com.competa.competademo.exceptions;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
public class UserAlreadyExistsException extends RestApiException {
    public UserAlreadyExistsException(final String template, Object... replacements) {
        super(BAD_REQUEST, arrayFormat(template, replacements).getMessage());
    }
}
