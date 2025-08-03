package com.br.devs.shared_restaurant.core.presenters;

import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.User;

public class UserPresenter {

    public static UserOutputDTO toDTO(User user) {
        return UserOutputDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .mail(user.getMail())
                .userType(user.getUserType())
                .address(user.getAddress() != null ? AddressPresenter.toDTO(user.getAddress()) : null)
                .build();
    }

    public static User toEntity(UserCreateDTO input) {
        return User.builder()
                .login(input.getLogin())
                .name(input.getName())
                .mail(input.getMail())
                .password(input.getPassword())
                .userType(input.getUserType())
                .address(input.getAddress() != null ? AddressPresenter.toEntity(input.getAddress()) : null)
                .build();
    }
}
