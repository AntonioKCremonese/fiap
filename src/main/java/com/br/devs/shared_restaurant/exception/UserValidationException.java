package com.br.devs.shared_restaurant.exception;

import java.io.Serial;

public final class UserValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    UserValidationException(String message) {
        super(message);
    }

    public static UserValidationException userNotFoundException() {
        return new UserValidationException("Usuário não encontrado.");
    }

    public static UserValidationException userAlreadyExistsException() {
        return new UserValidationException("Usuário com email ou login já cadastrado.");
    }

    public static UserValidationException confirmationPasswordNotValid() {
        return new UserValidationException("Senha de confirmação inválida");
    }

    public static UserValidationException existingPasswordNotValid() {
        return new UserValidationException("A senha atual informada não coincide com a senha cadastrada");
    }
}