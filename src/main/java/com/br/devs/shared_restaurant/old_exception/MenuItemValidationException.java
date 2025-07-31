package com.br.devs.shared_restaurant.old_exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public final class MenuItemValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus statusCode;

    MenuItemValidationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public static MenuItemValidationException menuItemNotFoundException() {
        return new MenuItemValidationException("Item do cardápio não encontrado.", HttpStatus.NOT_FOUND);
    }
}