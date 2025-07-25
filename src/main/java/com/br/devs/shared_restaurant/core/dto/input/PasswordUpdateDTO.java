package com.br.devs.shared_restaurant.core.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordUpdateDTO(

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
                message = "A senha deve conter letras maiúsculas, minúsculas e números")
        String newPassword,

        @NotBlank(message = "A confirmação de senha é obrigatória")
        String passwordConfirmation,

        @NotBlank(message = "A senha atual é obrigatória")
        String currentPassword) {
}