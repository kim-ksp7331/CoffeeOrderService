package com.codestates.response;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private int status;
    private String message;
    private List<FieldError> fieldErrors;
    private List<ConstraintViolationError> violationErrors;

    public static ErrorResponse of(BindingResult bindingResult) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ErrorResponse(status.value(), status.getReasonPhrase(), FieldError.of(bindingResult), null);
    }

    public static ErrorResponse of(BindingResult bindingResult, ErrorMapper mapper) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ErrorResponse(status.value(), status.getReasonPhrase(),
                mapper.bindingResultToErrorResponseFieldErrorList(bindingResult), null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ErrorResponse(status.value(), status.getReasonPhrase(), null, ConstraintViolationError.of(violations));
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations, ErrorMapper mapper) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ErrorResponse(status.value(), status.getReasonPhrase(),
                null, mapper.constraintViolationSetToConstraintViolationErrorList(violations));
    }

    public static ErrorResponse of(BusinessLogicException exception) {
        ExceptionCode exceptionCode = exception.getExceptionCode();
        return new ErrorResponse(exceptionCode.getStatus(), exception.getMessage(), null, null);
    }

    public static ErrorResponse of(HttpStatus status) {
        return new ErrorResponse(status.value(), status.getReasonPhrase(), null, null);
    }



    @Getter
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream().map(error -> new FieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()
            )).collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        public static List<ConstraintViolationError> of(Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream().map(constraintViolation -> new ConstraintViolationError(
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getInvalidValue().toString(),
                    constraintViolation.getMessage()
            )).collect(Collectors.toList());
        }
    }
}
