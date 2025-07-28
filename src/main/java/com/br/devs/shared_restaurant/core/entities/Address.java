package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.AddressOutputDTO;
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
        if (addressInputDTO == null) {
            return null;
        }
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

    public static Address create(AddressOutputDTO addressOutputDTO) {
        return Address.builder()
                .id(addressOutputDTO.getId())
                .street(addressOutputDTO.getStreet())
                .number(addressOutputDTO.getNumber())
                .complement(addressOutputDTO.getComplement())
                .neighborhood(addressOutputDTO.getNeighborhood())
                .city(addressOutputDTO.getCity())
                .state(addressOutputDTO.getState())
                .country(addressOutputDTO.getCountry())
                .postalCode(addressOutputDTO.getPostalCode())
                .reference(addressOutputDTO.getReference())
                .lastUpdate(OffsetDateTime.now())
                .build();
    }
}
