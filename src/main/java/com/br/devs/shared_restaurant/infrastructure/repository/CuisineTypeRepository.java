package com.br.devs.shared_restaurant.infrastructure.repository;

import com.br.devs.shared_restaurant.infrastructure.model.CuisineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineTypeRepository extends JpaRepository<CuisineTypeEntity, String> {

}