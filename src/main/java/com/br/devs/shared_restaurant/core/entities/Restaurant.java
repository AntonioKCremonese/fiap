package com.br.devs.shared_restaurant.core.entities;


import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
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
}
