package com.br.devs.shared_restaurant.repository;

import com.br.devs.shared_restaurant.model.CuisineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineTypeRepository extends JpaRepository<CuisineType, String> {

}