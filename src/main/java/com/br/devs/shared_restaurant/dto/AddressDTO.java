package com.br.devs.shared_restaurant.dto;

public record AddressDTO(
        String street,
        int number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String country,
        String postalCode,
        String reference) {
}
