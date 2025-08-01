package com.br.devs.shared_restaurant.core.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemOutputDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean availableForDineInOnly;
    private String photoPath;
    private RestaurantOutputDTO restaurant;
}