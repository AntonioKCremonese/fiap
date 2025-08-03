package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuisineType {
    private String id;
    private String name;

    public static void update(CuisineType existingCuisineType, CuisineTypeInputDTO cuisineTypeInputDTO) {
        existingCuisineType.setName(cuisineTypeInputDTO.getName());
    }
}
