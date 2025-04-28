package com.br.devs.shared_restaurant.infrastructure.entity;

import com.br.devs.shared_restaurant.core.domain.enums.UserTypeEnum;
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
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 125)
    private String name;

    @Column(length = 200)
    private String mail;

    @Column(length = 50)
    private String login;

    @Column(length = 50)
    private String password;

    @Column
    private LocalDate lastUpdate;

    @Column
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;

    @Column
    private UUID addressId;
}
