package com.br.devs.shared_restaurant.core.presenters;

import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.entities.CuisineType;

public class CuisinePresenter {

    public static CuisineType toEntity(CuisineTypeInputDTO cuisineTypeOutputDTO) {
        return new CuisineType(null, cuisineTypeOutputDTO.getName());
    }

    public static CuisineTypeOutputDTO toDTO(CuisineType cuisineType) {
        return new CuisineTypeOutputDTO(cuisineType.getId(), cuisineType.getName());
    }
}
