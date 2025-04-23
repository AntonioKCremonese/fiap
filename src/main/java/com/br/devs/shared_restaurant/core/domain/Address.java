package com.br.devs.shared_restaurant.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class Address {
    private UUID id;
    private String street;
    private int number;
    private String district;
    private String adjunct;
}
