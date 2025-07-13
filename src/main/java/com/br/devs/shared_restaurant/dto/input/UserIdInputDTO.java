package com.br.devs.shared_restaurant.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIdInputDTO {

    @NotNull
    private Long id;
}