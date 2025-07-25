package com.br.devs.shared_restaurant.core.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantInputDTO {

    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @Valid
    @NotNull
    private AddressInputDTO address;

    @Valid
    @NotNull
    private CuisineTypeIdInputDTO cuisineType;

    @Size(max = 500, message = "O horário de funcionamento deve ter no máximo 500 caracteres")
    @NotBlank(message = "O horário de funcionamento é obrigatório")
    private String openingHours;

    @Valid
    @NotNull
    private UserIdInputDTO owner;
}