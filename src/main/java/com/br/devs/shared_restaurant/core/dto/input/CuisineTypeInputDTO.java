package com.br.devs.shared_restaurant.core.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineTypeInputDTO {

    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @NotBlank(message = "O nome é obrigatório")
    private String name;
}