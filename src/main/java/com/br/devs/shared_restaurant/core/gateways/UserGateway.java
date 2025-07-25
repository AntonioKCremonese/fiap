package com.br.devs.shared_restaurant.core.gateways;

import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.interfaces.IDataSource;
import com.br.devs.shared_restaurant.core.interfaces.IUserGateway;

import java.util.Optional;

public class UserGateway implements IUserGateway {
    private IDataSource dataSource;

    @Override
    public Optional<User> findByLogin(String login) {
        UserOutputDTO userOutputDTO = dataSource.findByLogin(login);
        if (userOutputDTO == null) {
            throw UserValidationException.userNotFoundException();
        }
        return Optional.ofNullable(User.create(userOutputDTO.getId(), userOutputDTO));
    }

    @Override
    public Optional<User> findByMail(String mail) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }
}
