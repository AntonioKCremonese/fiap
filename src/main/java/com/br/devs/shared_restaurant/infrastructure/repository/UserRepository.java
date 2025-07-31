package com.br.devs.shared_restaurant.infrastructure.repository;

import com.br.devs.shared_restaurant.infrastructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByMail(String mail);
}