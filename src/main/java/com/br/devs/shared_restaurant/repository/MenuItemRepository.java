package com.br.devs.shared_restaurant.repository;

import com.br.devs.shared_restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, String> {
    Optional<MenuItem> findByNameAndRestaurant_Id(String name, String restaurantId);
}