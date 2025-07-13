package com.br.devs.shared_restaurant.dto.output;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;

public record UserOutputDTO(
        String id,
        String name,
        String email,
        UserTypeEnum userType,
        AddressOutputDTO address) {
}