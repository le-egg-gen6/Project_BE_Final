package com.myproject.project_oop.config.error;

import com.myproject.project_oop.config.error.exception.InvalidArgumentException;
import com.myproject.project_oop.config.error.exception.ResourceFetchException;
import com.myproject.project_oop.config.error.exception.ResourceNotFoundException;
import com.myproject.project_oop.dto.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleError404() {
        return buildResponseEntity("Requested resource does not exist!");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseResponse<?>> handleIO(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildResponseEntity("An error occurred in IO streams!");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<?>> handleAccessDenied() {
        return buildResponseEntity("Access denied!");
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<BaseResponse<?>> handleInvalidArgumentException(Exception e) {
        return buildResponseEntity(e.getMessage());
    }

    @ExceptionHandler(ResourceFetchException.class)
    public ResponseEntity<BaseResponse<?>> handleResourceFetchException(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildResponseEntity(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleResourceNotFoundException(Exception e) {
        return buildResponseEntity(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(error -> stringBuilder.append(String.format("%s : %s ", ((FieldError) error).getField(), error.getDefaultMessage())));
        return buildResponseEntity(stringBuilder.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleError(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildResponseEntity(e.getClass().getName() + " " + e.getMessage());
    }

    private ResponseEntity<BaseResponse<?>> buildResponseEntity(String message) {
        return ResponseEntity.ok(BaseResponse.buildErrorResponse(message));
    }

}
