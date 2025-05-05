package com.br.devs.shared_restaurant.repository;

import com.br.devs.shared_restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByMailOrLogin(String mail, String login);
    Optional<User> findByLogin(String login);
}