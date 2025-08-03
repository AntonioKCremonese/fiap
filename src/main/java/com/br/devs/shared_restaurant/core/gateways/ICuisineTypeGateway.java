package com.br.devs.shared_restaurant.core.gateways;

import com.br.devs.shared_restaurant.core.entities.CuisineType;

public interface ICuisineTypeGateway {

    CuisineType findCuisineTypeById(String id);
    CuisineType save(CuisineType cuisineType);
    void deleteCuisineTypeById(String id);
}
