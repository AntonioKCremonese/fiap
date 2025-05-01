package com.br.devs.shared_restaurant.exception;

import java.io.Serial;

public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException() {
        super("Usuário com mail ou login já cadastrado");
    }
}
