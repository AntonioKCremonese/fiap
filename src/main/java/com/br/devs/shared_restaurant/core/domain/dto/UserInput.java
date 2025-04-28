package com.br.devs.shared_restaurant.core.domain.dto;

import com.br.devs.shared_restaurant.core.domain.model.enums.UserTypeEnum;

public record UserInput(
        String name,
        String email,
        String login,
        String password,
        String passwordConfirmation,
        UserTypeEnum userType) {
}