package com.br.devs.shared_restaurant.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemInputDTO {

    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String description;

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "Informe se o item está disponível para entrega ou apenas para consumo no local")
    private boolean availableForDineInOnly;

    @Size(max = 500, message = "A URL da foto deve ter no máximo 500 caracteres")
    private String photoPath;

    @Valid
    @NotNull
    private RestaurantIdInputDTO restaurant;
}