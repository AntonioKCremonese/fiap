package com.br.devs.shared_restaurant.core.domain;

import com.br.devs.shared_restaurant.core.domain.enums.UserTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private UUID id;
    private String name;
    private String mail;
    private String login;
    private String password;
    private LocalDate lastUpdate;
    private UserTypeEnum userType;
    private Address address;
}
