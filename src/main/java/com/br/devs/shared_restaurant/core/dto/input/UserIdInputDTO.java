package com.br.devs.shared_restaurant.core.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIdInputDTO {

    @NotNull
    private String id;
}