package com.br.devs.shared_restaurant.exception;

import java.io.Serial;

public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException() {
        super("Usuário com email ou login já cadastrado");
    }
}
