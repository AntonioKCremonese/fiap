package com.br.devs.shared_restaurant.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineTypeIdInputDTO {

    @NotNull
    private String id;
}