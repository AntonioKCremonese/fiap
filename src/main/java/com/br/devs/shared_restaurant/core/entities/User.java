package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
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

    public static User create(UserOutputDTO userOutputDTO, Address address) {
        if (userOutputDTO == null) {
            return null;
        }
        return User.builder()
                .id(userOutputDTO.getId())
                .name(userOutputDTO.getName())
                .mail(userOutputDTO.getMail())
                .login(userOutputDTO.getLogin())
                .userType(userOutputDTO.getUserType())
                .lastUpdate(OffsetDateTime.now())
                .address(address)
                .build();
    }

    public static User update(User user, UserUpdateDTO userUpdateDTO) {
        user.setName(userUpdateDTO.getName());
        user.setMail(userUpdateDTO.getMail());
        user.setLogin(userUpdateDTO.getLogin());
        user.setUserType(userUpdateDTO.getUserType());
        user.setLastUpdate(OffsetDateTime.now());
        return user;
    }

    public static User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setLastUpdate(OffsetDateTime.now());
        return user;
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

    public static User updateAddress(User existingUser, AddressInputDTO input) {
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
        return existingUser;
    }
}
