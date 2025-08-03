package com.br.devs.shared_restaurant.core.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInputDTO {

    @Size(max = 255, message = "O nome da rua deve ter no máximo 255 caracteres")
    @NotBlank(message = "A rua é obrigatória")
    private String street;

    @Max(value = 100_000, message = "O número deve ser menor que 100.000")
    @NotNull(message = "O número é obrigatório")
    private Integer number;

    @Size(max = 255, message = "O complemento deve ter no máximo 255 caracteres")
    private String complement;

    @Size(max = 255, message = "O nome do bairro deve ter no máximo 255 caracteres")
    @NotBlank(message = "O bairro é obrigatório")
    private String neighborhood;

    @Size(max = 100, message = "O nome da cidade deve ter no máximo 100 caracteres")
    @NotBlank(message = "A cidade é obrigatória")
    private String city;

    @Size(max = 100, message = "O nome do estado deve ter no máximo 100 caracteres")
    @NotBlank(message = "O estado é obrigatório")
    private String state;

    @Size(max = 70, message = "O nome do país deve ter no máximo 70 caracteres")
    @NotBlank(message = "O país é obrigatório")
    private String country;

    @Size(max = 20, message = "O CEP deve ter no máximo 20 caracteres")
    @NotBlank(message = "O CEP é obrigatório")
    private String postalCode;

    @Size(max = 100, message = "O ponto de referência deve ter no máximo 100 caracteres")
    private String reference;
}