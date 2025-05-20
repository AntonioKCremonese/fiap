package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.dto.AddressInputDTO;
import com.br.devs.shared_restaurant.dto.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.dto.UserCreateDTO;
import com.br.devs.shared_restaurant.dto.UserOutputDTO;
import com.br.devs.shared_restaurant.dto.UserUpdateDTO;
import com.br.devs.shared_restaurant.exception.UserValidationException;
import com.br.devs.shared_restaurant.mapper.AddressMapper;
import com.br.devs.shared_restaurant.mapper.UserMapper;
import com.br.devs.shared_restaurant.model.User;
import com.br.devs.shared_restaurant.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserOutputDTO getUserById(String id) {
        return UserMapper.fromEntity(findById(id));
    }

    @Transactional
    public UserOutputDTO createUser(UserCreateDTO input) {
        validatePasswordConfirmation(input.password(), input.passwordConfirmation());

        userRepository.findByLogin(input.login()).ifPresent(user -> {
            if (user.getLogin().equals(input.login())) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este login.");
            }
        });

        userRepository.findByMail(input.mail()).ifPresent(user -> {
            if (user.getMail().equals(input.mail())) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este e-mail.");
            }
        });

        User user = UserMapper.toEntity(input);
        return UserMapper.fromEntity(userRepository.save(user));
    }

    @Transactional
    public UserOutputDTO updateUser(String id, UserUpdateDTO input) {
        User existingUser = findById(id);

        userRepository.findByLogin(input.login()).ifPresent(user -> {
            if (user.getLogin().equals(input.login()) && !existingUser.equals(user)) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este login");
            }
        });

        userRepository.findByMail(input.mail()).ifPresent(user -> {
            if (user.getMail().equals(input.mail()) && !existingUser.equals(user)) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este e-mail.");
            }
        });

        UserMapper.copyToEntity(existingUser, input);
        return UserMapper.fromEntity(userRepository.save(existingUser));
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.delete(findById(id));
    }

    @Transactional
    public void updatePassword(String id, PasswordUpdateDTO input) {
        validatePasswordConfirmation(input.newPassword(), input.passwordConfirmation());
        User user = findById(id);

        if (!user.getPassword().equals(input.currentPassword())) {
            throw UserValidationException.existingPasswordNotValid();
        }

        user.updatePassword(input.newPassword());
        userRepository.save(user);
    }

    @Transactional
    public UserOutputDTO updateUserAddress(String userId, AddressInputDTO input) {
        var user = findById(userId);
        user.setAddress(AddressMapper.toEntity(input));
        return UserMapper.fromEntity(userRepository.save(user));
    }

    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(UserValidationException::userNotFoundException);
    }

    protected User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(UserValidationException::userNotFoundException);
    }

    private static void validatePasswordConfirmation(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw UserValidationException.confirmationPasswordNotValid();
        }
    }
}