package com.br.devs.shared_restaurant.dto;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @Size(max = 125, message = "O nome deve ter no máximo 125 caracteres")
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @Size(max = 200, message = "O e-mail deve ter no máximo 200 caracteres")
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String mail,

        @Size(max = 50, message = "O login deve ter no máximo 50 caracteres")
        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
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