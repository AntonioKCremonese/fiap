package com.br.devs.shared_restaurant.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public final class CuisineTypeValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus statusCode;

    CuisineTypeValidationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public static CuisineTypeValidationException cuisineTypeNotFoundException() {
        return new CuisineTypeValidationException("Tipo de cozinha não encontrado.", HttpStatus.NOT_FOUND);
    }

    public static CuisineTypeValidationException cuisineTypeInUseException() {
        return new CuisineTypeValidationException("O tipo de cozinha está em uso e não pode ser removido.", HttpStatus.BAD_REQUEST);
    }
}