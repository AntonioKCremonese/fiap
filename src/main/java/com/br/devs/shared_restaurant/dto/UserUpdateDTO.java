package com.br.devs.shared_restaurant.dto;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @Email(message = "O e-mail deve ser válido")
        String mail,

        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotNull(message = "O tipo de usuário é obrigatório")
        UserTypeEnum userType) {
}