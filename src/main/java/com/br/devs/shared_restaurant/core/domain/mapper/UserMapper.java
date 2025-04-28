package com.br.devs.shared_restaurant.core.domain.mapper;

import com.br.devs.shared_restaurant.core.domain.dto.UserInput;
import com.br.devs.shared_restaurant.core.domain.dto.UserOutput;
import com.br.devs.shared_restaurant.core.domain.model.User;

public final class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static UserOutput fromEntity(User user) {
        return new UserOutput(user.getId(), user.getName(), user.getEmail(), user.getUserType());
    }

    public static User toEntity(UserInput input) {
        return new User(
                input.name(),
                input.email(),
                input.login(),
                input.password(),
                input.userType()
        );
    }
}