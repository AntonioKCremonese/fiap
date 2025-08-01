package com.br.devs.shared_restaurant.core.gateways;

import com.br.devs.shared_restaurant.core.entities.Restaurant;

public interface IRestaurantGateway {
    Restaurant save(Restaurant restaurant);
    Restaurant findRestaurantById(String id);
    void deleteRestaurantById(String id);
}
