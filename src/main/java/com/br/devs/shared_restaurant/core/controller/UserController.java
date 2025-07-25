package com.br.devs.shared_restaurant.core.controller;

import com.br.devs.shared_restaurant.core.interfaces.IDataSource;
import com.br.devs.shared_restaurant.core.usecases.UserUseCase;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;

public class UserController {

    private IDataSource datasource;

    public UserCreateDTO createUser() {
        UserUseCase.create();
    }
}
