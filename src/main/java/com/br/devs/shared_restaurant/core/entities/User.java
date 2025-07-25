package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class User {
    private String id;
    private String name;
    private String mail;
    private String login;
    private String password;
    private OffsetDateTime lastUpdate;
    private UserTypeEnum userType;
    private Address address;

    public static User create(String id, UserCreateDTO userCreateDTO, Address address) {
        return User.builder()
                .id(id)
                .name(userCreateDTO.getName())
                .mail(userCreateDTO.getMail())
                .login(userCreateDTO.getLogin())
                .password(userCreateDTO.getPassword())
                .userType(userCreateDTO.getUserType())
                .lastUpdate(OffsetDateTime.now())
                .address(address)
                .build();
    }

    public static void validatePasswordConfirmation(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw UserValidationException.confirmationPasswordNotValid();
        }
    }

    public static void validateLoginAlreadyExists(String login, String loginToCreate) {
        if (login.equals(loginToCreate)) {
            throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este login.");
        }
    }

    public static void validateMailAlreadyExists(String mail, String mailToCreate) {
        if (mail.equals(mailToCreate)) {
            throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este e-mail.");
        }
    }
}
