package com.br.devs.shared_restaurant.core.entities;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
}
