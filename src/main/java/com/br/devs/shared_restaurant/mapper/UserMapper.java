package com.br.devs.shared_restaurant.mapper;

import com.br.devs.shared_restaurant.dto.AddressOutputDTO;
import com.br.devs.shared_restaurant.dto.UserCreateDTO;
import com.br.devs.shared_restaurant.dto.UserOutputDTO;
import com.br.devs.shared_restaurant.dto.UserUpdateDTO;
import com.br.devs.shared_restaurant.model.Address;
import com.br.devs.shared_restaurant.model.User;

public class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static UserOutputDTO fromEntity(User user) {
        AddressOutputDTO address = null;

        if (user.getAddress() != null) {
            address = AddressMapper.fromEntity(user.getAddress());
        }

        return new UserOutputDTO(user.getId(), user.getName(), user.getMail(), user.getUserType(), address);
    }

    public static User toEntity(UserCreateDTO input) {
        Address address = null;

        if (input.address() != null) {
            address = AddressMapper.toEntity(input.address());
        }

        return new User(input.name(), input.mail(), input.login(), input.password(), input.userType(), address);
    }

    public static void copyToEntity(User user, UserUpdateDTO input) {
        user.setName(input.name());
        user.setMail(input.mail());
        user.setLogin(input.login());
        user.setUserType(input.userType());
    }
}