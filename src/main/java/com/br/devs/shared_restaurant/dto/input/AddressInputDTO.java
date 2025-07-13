package com.br.devs.shared_restaurant.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressInputDTO(

        @Size(max = 255, message = "O nome da rua deve ter no máximo 255 caracteres")
        @NotBlank(message = "A rua é obrigatória")
        String street,

        @Max(value = 100_000, message = "O número deve ser menor que 100.000")
        @NotNull(message = "O número é obrigatório")
        Integer number,

        @Size(max = 255, message = "O complemento deve ter no máximo 255 caracteres")
        String complement,

        @Size(max = 255, message = "O nome do bairro deve ter no máximo 255 caracteres")
        @NotBlank(message = "O bairro é obrigatório")
        String neighborhood,

        @Size(max = 100, message = "O nome da cidade deve ter no máximo 100 caracteres")
        @NotBlank(message = "A cidade é obrigatória")
        String city,

        @Size(max = 100, message = "O nome do estado deve ter no máximo 100 caracteres")
        @NotBlank(message = "O estado é obrigatório")
        String state,

        @Size(max = 70, message = "O nome do país deve ter no máximo 70 caracteres")
        @NotBlank(message = "O país é obrigatório")
        String country,

        @Size(max = 20, message = "O CEP deve ter no máximo 20 caracteres")
        @NotBlank(message = "O CEP é obrigatório")
        String postalCode,

        @Size(max = 100, message = "O ponto de referência deve ter no máximo 100 caracteres")
        String reference) {
}