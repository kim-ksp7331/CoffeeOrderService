package com.codestates.advice;

import com.codestates.exception.BusinessLogicException;
import com.codestates.response.ErrorMapper;
import com.codestates.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {
    private final ErrorMapper mapper;
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException e) {
        ErrorResponse response = ErrorResponse.of(e.getConstraintViolations(), mapper);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ErrorResponse response = ErrorResponse.of(e.getBindingResult(), mapper);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBusinessLogicException(BusinessLogicException e) {
        ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorResponse response = ErrorResponse.of(status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        System.out.println(e.toString());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.of(status);
        return new ResponseEntity<>(response, status);
    }
}
