package com.br.devs.shared_restaurant.dto;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;

public record UserOutput(
        String id,
        String name,
        String email,
        UserTypeEnum userType) {
}