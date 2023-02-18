package com.codestates.response;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ErrorMapper {
    @Mapping(source = "defaultMessage", target = "reason")
    ErrorResponse.FieldError fieldErrorToErrorResponseFieldError(FieldError fieldError);
    List<ErrorResponse.FieldError> fieldErrorListToErrorResponseFieldErrorList(List<FieldError> fieldErrors);

    default List<ErrorResponse.FieldError> bindingResultToErrorResponseFieldErrorList(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrorListToErrorResponseFieldErrorList(fieldErrors);
    }

    @Mapping(source = "invalidValue", target = "rejectedValue")
    @Mapping(source = "message", target = "reason")
    @Mapping(target = "propertyPath", qualifiedByName = "PathToString")
    ErrorResponse.ConstraintViolationError constraintviolationToConstraintViolationError(
            ConstraintViolation<?> constraintViolation);

    List<ErrorResponse.ConstraintViolationError> constraintViolationSetToConstraintViolationErrorList(
            Set<ConstraintViolation<?>> constraintViolations);

    @Named("PathToString")
    default String pathToString(Path path) {
        return path.toString();
    }
}
