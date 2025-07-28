package com.br.devs.shared_restaurant.core.controller;

import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.gateways.UserGateway;
import com.br.devs.shared_restaurant.core.interfaces.IDataSource;
import com.br.devs.shared_restaurant.core.presenters.UserPresenter;
import com.br.devs.shared_restaurant.core.usecases.UserUseCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserController {

    private final IDataSource datasource;

     private UserController(IDataSource dataSource) {
        this.datasource = dataSource;
    }

    public static UserController create(IDataSource dataSource) {
        return new UserController(dataSource);
    }

    public UserOutputDTO createUser(UserCreateDTO userCreateDTO) {
        log.info("Criando usuário: {}", userCreateDTO.getName());
        UserGateway userGateway = UserGateway.create(datasource);
        UserUseCase userUseCase = UserUseCase.create(userGateway);
        User user = userUseCase.createUser(userCreateDTO);
        log.info("Criou usuário: {}", user.getId());
        return UserPresenter.toDTO(user);
    }
}
