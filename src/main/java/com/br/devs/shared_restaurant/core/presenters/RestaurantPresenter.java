package com.br.devs.shared_restaurant.core.presenters;

import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.core.entities.Restaurant;

public class RestaurantPresenter {

    public static RestaurantOutputDTO toDTO(Restaurant restaurant) {
        return new RestaurantOutputDTO(
            restaurant.getId(),
            restaurant.getName(),
            AddressPresenter.toDTO(restaurant.getAddress()),
            CuisinePresenter.toDTO(restaurant.getCuisineType()),
            restaurant.getOpeningHours(),
            UserPresenter.toDTO(restaurant.getOwner())
        );
    }

    public static Restaurant toEntity(RestaurantInputDTO restaurantInputDTO) {
        return new Restaurant(
                null,
                restaurantInputDTO.getName(),
                AddressPresenter.toEntity(restaurantInputDTO.getAddress()),
                null,
                restaurantInputDTO.getOpeningHours(),
                null
        );
    }
}
