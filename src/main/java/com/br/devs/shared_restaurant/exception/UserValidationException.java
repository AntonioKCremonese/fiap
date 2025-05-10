package com.br.devs.shared_restaurant.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public final class UserValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus statusCode;

    UserValidationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public static UserValidationException userNotFoundException() {
        return new UserValidationException("Usuário não encontrado.", HttpStatus.NOT_FOUND);
    }

    public static UserValidationException userAlreadyExistsException(String message) {
        return new UserValidationException(message, HttpStatus.BAD_REQUEST);
    }

    public static UserValidationException confirmationPasswordNotValid() {
        return new UserValidationException("Senha de confirmação inválida", HttpStatus.BAD_REQUEST);
    }

    public static UserValidationException existingPasswordNotValid() {
        return new UserValidationException("A senha atual informada não coincide com a senha cadastrada", HttpStatus.BAD_REQUEST);
    }
}