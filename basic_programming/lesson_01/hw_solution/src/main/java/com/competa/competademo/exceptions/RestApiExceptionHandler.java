package com.competa.competademo.exceptions;

import com.competa.competademo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

/**
 * @author Oleg Karimov
 */
@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectUserIdException(RestApiException e) {
        ErrorResponseDto responseDto = new ErrorResponseDto(e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDto> handleMaxSizeException(MaxUploadSizeExceededException e) {
        ErrorResponseDto responseDto = new ErrorResponseDto(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationFiledError> fieldErrors = buildFieldErrors(exception.getBindingResult().getAllErrors());
        ValidationError validationError = new ValidationError(fieldErrors);
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    private List<ValidationFiledError> buildFieldErrors(List<ObjectError> errors) {
        return errors.stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .map(error -> new ValidationFiledError(error.getField(), error.getDefaultMessage()))
                .toList();
    }
}
