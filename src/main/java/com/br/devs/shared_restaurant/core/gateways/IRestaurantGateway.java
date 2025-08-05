package com.br.devs.shared_restaurant.core.gateways;

import com.br.devs.shared_restaurant.core.entities.Restaurant;

import java.util.Optional;

public interface IRestaurantGateway {
    Restaurant save(Restaurant restaurant);
    Restaurant findRestaurantById(String id);
    Optional<Restaurant> findRestaurantByName(String name);
    void deleteRestaurantById(String id);
}
