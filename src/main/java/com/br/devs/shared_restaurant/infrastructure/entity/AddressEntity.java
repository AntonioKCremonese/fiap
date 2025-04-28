package com.br.devs.shared_restaurant.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String street;

    @Column
    private int number;

    @Column
    private String district;

    @Column
    private String adjunct;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 70)
    private String country;

    @Column
    private LocalDate lastUpdate;
}
