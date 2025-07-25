package com.br.devs.shared_restaurant.core.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantOutputDTO {
    private String id;
    private String name;
    private AddressOutputDTO address;
    private CuisineTypeOutputDTO cuisineType;
    private String openingHours;
    private UserOutputDTO owner;
}