package com.br.devs.shared_restaurant.core.presenters;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.AddressOutputDTO;
import com.br.devs.shared_restaurant.core.entities.Address;

import java.time.OffsetDateTime;

public class AddressPresenter {

    public static Address toEntity(AddressOutputDTO addressOutputDTO) {
        return new Address(
                addressOutputDTO.getId(),
                addressOutputDTO.getStreet(),
                addressOutputDTO.getNumber(),
                addressOutputDTO.getComplement(),
                addressOutputDTO.getNeighborhood(),
                addressOutputDTO.getCity(),
                addressOutputDTO.getState(),
                addressOutputDTO.getCountry(),
                addressOutputDTO.getPostalCode(),
                addressOutputDTO.getReference(),
                OffsetDateTime.now()
        );
    }

    public static Address toEntity(AddressInputDTO addressInputDTO) {
        return new Address(
                null,
                addressInputDTO.getStreet(),
                addressInputDTO.getNumber(),
                addressInputDTO.getComplement(),
                addressInputDTO.getNeighborhood(),
                addressInputDTO.getCity(),
                addressInputDTO.getState(),
                addressInputDTO.getCountry(),
                addressInputDTO.getPostalCode(),
                addressInputDTO.getReference(),
                OffsetDateTime.now()
        );
    }

    public static AddressOutputDTO toDTO(Address address) {
        return new AddressOutputDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getPostalCode(),
                address.getReference()
        );
    }
}
