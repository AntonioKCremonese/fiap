package com.br.devs.shared_restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserValidationException(UserValidationException ex) {
        var error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler(CuisineTypeValidationException.class)
    public ResponseEntity<ErrorResponse> handleCuisineTypeValidationException(CuisineTypeValidationException ex) {
        var error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler(RestaurantValidationException.class)
    public ResponseEntity<ErrorResponse> handleRestaurantValidationException(RestaurantValidationException ex) {
        var error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler(MenuItemValidationException.class)
    public ResponseEntity<ErrorResponse> handleMenuItemValidationException(MenuItemValidationException ex) {
        var error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }

        var error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Campos inválidos",
                validationErrors
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        String detail = String.format("O recurso %s não existe.", ex.getResourcePath());

        var error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                detail
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUncaughtException() {
        var error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Houve um erro ao executar esta operação"
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}