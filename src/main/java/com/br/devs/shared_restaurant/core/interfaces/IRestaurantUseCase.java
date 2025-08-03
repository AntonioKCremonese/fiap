package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.core.entities.Restaurant;

public interface IRestaurantUseCase {
    RestaurantOutputDTO create(RestaurantInputDTO input);
    RestaurantOutputDTO getById(String id);
    RestaurantOutputDTO update(String id, RestaurantInputDTO restaurantInputDTO);
    void delete(String id);
    Restaurant findById(String id);
}
