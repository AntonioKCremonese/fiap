package com.br.devs.shared_restaurant.infrastructure.repository;

import com.br.devs.shared_restaurant.infrastructure.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, String> {

    Optional<RestaurantEntity> findByName(String name);
}