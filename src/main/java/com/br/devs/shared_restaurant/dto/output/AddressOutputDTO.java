package com.br.devs.shared_restaurant.dto.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressOutputDTO {
    private String id;
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String reference;
}