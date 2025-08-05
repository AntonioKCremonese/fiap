package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.exceptions.CuisineTypeValidationException;
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

    public static void validateNameAlreadyExists(String name, String nameToCreate) {
        if (name.equals(nameToCreate)) {
            throw CuisineTypeValidationException.cuisineTypeAlreadyExistsException("Já existe um tipo de culinária cadastrado com este nome.");
        }
    }
}
