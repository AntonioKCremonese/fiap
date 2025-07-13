package com.br.devs.shared_restaurant.mapper;

import com.br.devs.shared_restaurant.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.dto.output.AddressOutputDTO;
import com.br.devs.shared_restaurant.model.Address;

public class AddressMapper {

    private AddressMapper() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static AddressOutputDTO fromEntity(Address address) {
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

    public static Address toEntity(AddressInputDTO input) {
        var address = new Address();
        address.setStreet(input.street());
        address.setNumber(input.number());
        address.setComplement(input.complement());
        address.setNeighborhood(input.neighborhood());
        address.setCity(input.city());
        address.setState(input.state());
        address.setCountry(input.country());
        address.setPostalCode(input.postalCode());
        address.setReference(input.reference());
        return address;
    }
}