package com.br.devs.shared_restaurant.infrastructure.repository;

import com.br.devs.shared_restaurant.infrastructure.model.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, String> {
    Optional<MenuItemEntity> findByNameAndRestaurant_Id(String name, String restaurantId);
}