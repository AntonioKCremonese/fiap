package com.br.devs.shared_restaurant.dto;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;

public record UserInput(
        String name,
        String mail,
        String login,
        String password,
        String passwordConfirmation,
        UserTypeEnum userType,
        AddressDTO address) {
}