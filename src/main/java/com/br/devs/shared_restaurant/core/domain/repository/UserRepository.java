package com.br.devs.shared_restaurant.core.domain.repository;

import com.br.devs.shared_restaurant.core.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}