package com.br.devs.shared_restaurant.dto;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @Email(message = "O e-mail deve ser válido")
        String mail,

        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "a senha deve ter pelo menos 8 caracteres")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
                message = "A senha deve conter letras maiúsculas, minúsculas e números")
        String password,

        @NotBlank(message = "A confirmação de senha é obrigatória")
        String passwordConfirmation,

        @NotNull(message = "O tipo de usuário é obrigatório")
        UserTypeEnum userType,

        @Valid
        AddressInputDTO address) {
}