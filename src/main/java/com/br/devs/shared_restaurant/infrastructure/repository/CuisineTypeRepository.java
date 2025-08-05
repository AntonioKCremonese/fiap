package com.br.devs.shared_restaurant.infrastructure.repository;

import com.br.devs.shared_restaurant.infrastructure.model.CuisineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuisineTypeRepository extends JpaRepository<CuisineTypeEntity, String> {

    Optional<CuisineTypeEntity> findByName(String name);
}