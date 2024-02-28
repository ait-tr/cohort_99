package com.competa.competademo.exceptions;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * @author Konstantin Glazunov
 * created on 18-Jan-24 1
 */
public class NotSupportedImageFileException extends RestApiException{

    public NotSupportedImageFileException(final String template, Object... replacements){
        super(UNPROCESSABLE_ENTITY, arrayFormat(template, replacements).getMessage());
    }
}
