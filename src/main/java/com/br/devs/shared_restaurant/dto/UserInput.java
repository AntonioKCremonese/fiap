package com.br.devs.shared_restaurant.dto;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;
import jakarta.validation.constraints.*;

public record UserInput(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @Email(message = "Email deve ser válido")
        String mail,
        @NotBlank(message = "Login é obrigatório")
        String login,
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
                message = "A senha deve conter letras maiúsculas, minúsculas e números")
        String password,
        String passwordConfirmation,
        @NotNull(message = "Tipo de usuário é obrigatório")
        UserTypeEnum userType,
        AddressDTO address) {
}