package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.entities.CuisineType;

public interface ICuisineTypeUseCase {
    CuisineTypeOutputDTO getById(String id);
    CuisineTypeOutputDTO create(CuisineTypeInputDTO cuisineTypeInputDTO);
    CuisineTypeOutputDTO update(String id, CuisineTypeInputDTO cuisineTypeInputDTO);
    void delete(String id);
    CuisineType findById(String id);
}
