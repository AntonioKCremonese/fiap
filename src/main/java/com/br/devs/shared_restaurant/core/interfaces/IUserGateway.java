package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.entities.User;

import java.util.Optional;

public interface IUserGateway {

    Optional<User> findByLogin(String login);
    Optional<User> findByMail(String mail);
    Optional<User> findById(String id);
    User save(User user);
}
