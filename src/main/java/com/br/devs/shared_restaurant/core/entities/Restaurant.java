package com.br.devs.shared_restaurant.core.entities;


import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.exceptions.RestaurantValidationException;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private String id;
    private String name;
    private Address address;
    private CuisineType cuisineType;
    private String openingHours;
    private User owner;

    public static void update(Restaurant existingRestaurant, RestaurantInputDTO restaurantInputDTO) {
        existingRestaurant.setName(restaurantInputDTO.getName());
        existingRestaurant.setOpeningHours(restaurantInputDTO.getOpeningHours());
    }

    public static void validateNameAlreadyExists(String name, String nameToCreate) {
        if (name.equals(nameToCreate)) {
            throw RestaurantValidationException.restaurantAlreadyExistsException("Já existe um restaurante cadastrado com este nome.");
        }
    }
}
