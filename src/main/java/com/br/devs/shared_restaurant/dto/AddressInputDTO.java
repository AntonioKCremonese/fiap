package com.br.devs.shared_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressInputDTO(

        @NotBlank(message = "A rua é obrigatória")
        String street,

        @NotNull(message = "O número é obrigatório")
        Integer number,

        String complement,

        @NotBlank(message = "O bairro é obrigatório")
        String neighborhood,

        @NotBlank(message = "A cidade é obrigatória")
        String city,

        @NotBlank(message = "O estado é obrigatório")
        String state,

        @NotBlank(message = "O país é obrigatório")
        String country,

        @NotBlank(message = "O CEP é obrigatório")
        String postalCode,

        String reference) {
}