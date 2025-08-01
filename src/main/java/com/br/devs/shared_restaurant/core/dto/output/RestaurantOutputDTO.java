package com.br.devs.shared_restaurant.core.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantOutputDTO {
    private String id;
    private String name;
    private AddressOutputDTO address;
    private CuisineTypeOutputDTO cuisineType;
    private String openingHours;
    private UserOutputDTO owner;
}