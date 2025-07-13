package com.br.devs.shared_restaurant.dto.output;

public record AddressOutputDTO(
        String id,
        String street,
        Integer number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String country,
        String postalCode,
        String reference) {
}