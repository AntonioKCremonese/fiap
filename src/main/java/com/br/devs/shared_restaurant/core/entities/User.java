package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String mail;
    private String login;
    private String password;
    private OffsetDateTime lastUpdate;
    private UserTypeEnum userType;
    private Address address;

    public static void update(User user, UserUpdateDTO userUpdateDTO) {
        user.setName(userUpdateDTO.getName());
        user.setMail(userUpdateDTO.getMail());
        user.setLogin(userUpdateDTO.getLogin());
        user.setUserType(userUpdateDTO.getUserType());
        user.setLastUpdate(OffsetDateTime.now());
    }

    public static void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setLastUpdate(OffsetDateTime.now());
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

    public static void validateCurrentPassword(String password, String currentPassword) {
        if (!password.equals(currentPassword)) {
            throw UserValidationException.existingPasswordNotValid();
        }
    }

    public static void updateAddress(User existingUser, AddressInputDTO input) {
        if (existingUser.getAddress() == null) {
            existingUser.setAddress(new Address());
        }
        existingUser.getAddress().setStreet(input.getStreet());
        existingUser.getAddress().setNumber(input.getNumber());
        existingUser.getAddress().setComplement(input.getComplement());
        existingUser.getAddress().setNeighborhood(input.getNeighborhood());
        existingUser.getAddress().setCity(input.getCity());
        existingUser.getAddress().setState(input.getState());
        existingUser.getAddress().setCountry(input.getCountry());
        existingUser.getAddress().setPostalCode(input.getPostalCode());
        existingUser.getAddress().setReference(input.getReference());
        existingUser.setLastUpdate(OffsetDateTime.now());
    }
}
