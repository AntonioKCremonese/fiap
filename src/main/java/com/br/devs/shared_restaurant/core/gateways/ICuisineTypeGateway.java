package com.br.devs.shared_restaurant.core.gateways;

import com.br.devs.shared_restaurant.core.entities.CuisineType;

import java.util.Optional;

public interface ICuisineTypeGateway {

    CuisineType findCuisineTypeById(String id);
    Optional<CuisineType> findCuisineTypeByName(String name);
    CuisineType save(CuisineType cuisineType);
    void deleteCuisineTypeById(String id);
}
