package com.br.devs.shared_restaurant.old_exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public final class RestaurantValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus statusCode;

    RestaurantValidationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public static RestaurantValidationException restaurantNotFoundException() {
        return new RestaurantValidationException("Restaurante não encontrado.", HttpStatus.NOT_FOUND);
    }

    public static RestaurantValidationException restaurantInUseException() {
        return new RestaurantValidationException("O restaurante está em uso e não pode ser removido.", HttpStatus.BAD_REQUEST);
    }
}