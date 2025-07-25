package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Address {

    private String id;
    private String street;
    private int number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String reference;
    private OffsetDateTime lastUpdate;

    public static Address create(String id, AddressInputDTO addressInputDTO) {
        return Address.builder()
                .id(id)
                .street(addressInputDTO.getStreet())
                .number(addressInputDTO.getNumber())
                .complement(addressInputDTO.getComplement())
                .neighborhood(addressInputDTO.getNeighborhood())
                .city(addressInputDTO.getCity())
                .state(addressInputDTO.getState())
                .country(addressInputDTO.getCountry())
                .postalCode(addressInputDTO.getPostalCode())
                .reference(addressInputDTO.getReference())
                .lastUpdate(OffsetDateTime.now())
                .build();
    }
}
