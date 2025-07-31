package com.br.devs.shared_restaurant.core.presenters;

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
                // TODO AddressOutputDTO
                .build();
    }
}
