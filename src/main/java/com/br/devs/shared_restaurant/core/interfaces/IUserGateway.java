package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.entities.User;

import java.util.Optional;

public interface IUserGateway {

    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByMail(String mail);
    Optional<User> findUserById(String id);
    User save(User user);
    void deleteUserById(String userId);
}
