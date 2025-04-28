package com.br.devs.shared_restaurant.core.domain.dto;

import com.br.devs.shared_restaurant.core.domain.model.enums.UserTypeEnum;

public record UserOutput(
        String id,
        String name,
        String email,
        UserTypeEnum userType) {
}