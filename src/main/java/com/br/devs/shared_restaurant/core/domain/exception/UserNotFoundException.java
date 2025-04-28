package com.br.devs.shared_restaurant.core.domain.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super("Usuário não encontrado.");
    }
}