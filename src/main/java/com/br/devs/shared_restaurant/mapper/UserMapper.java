package com.br.devs.shared_restaurant.mapper;

import com.br.devs.shared_restaurant.dto.UserInput;
import com.br.devs.shared_restaurant.dto.UserOutput;
import com.br.devs.shared_restaurant.model.User;

public class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static UserOutput fromEntity(User user) {
        return new UserOutput(user.getId(), user.getName(), user.getMail(), user.getUserType());
    }

    public static User toEntity(UserInput input) {
        return new User(
                input.name(),
                input.mail(),
                input.login(),
                input.password(),
                input.userType()
        );
    }
}