package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.infrastructure.model.Address;
import com.br.devs.shared_restaurant.infrastructure.model.User;
import com.br.devs.shared_restaurant.infrastructure.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public UserOutputDTO getUserById(String id) {
        User user = findById(id);
        return modelMapper.map(user, UserOutputDTO.class);
    }

    @Transactional
    public UserOutputDTO createUser(UserCreateDTO input) {
        validatePasswordConfirmation(input.getPassword(), input.getPasswordConfirmation());

        userRepository.findByLogin(input.getLogin()).ifPresent(user -> {
            if (user.getLogin().equals(input.getLogin())) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este login.");
            }
        });

        userRepository.findByMail(input.getMail()).ifPresent(user -> {
            if (user.getMail().equals(input.getMail())) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este e-mail.");
            }
        });

        User user = modelMapper.map(input, User.class);
        return modelMapper.map(userRepository.save(user), UserOutputDTO.class);
    }

    @Transactional
    public UserOutputDTO updateUser(String id, UserUpdateDTO input) {
        User existingUser = findById(id);

        userRepository.findByLogin(input.getLogin()).ifPresent(user -> {
            if (user.getLogin().equals(input.getLogin()) && !existingUser.equals(user)) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este login");
            }
        });

        userRepository.findByMail(input.getMail()).ifPresent(user -> {
            if (user.getMail().equals(input.getMail()) && !existingUser.equals(user)) {
                throw UserValidationException.userAlreadyExistsException("Já existe um usuário cadastrado com este e-mail.");
            }
        });

        modelMapper.map(input, existingUser);
        return modelMapper.map(userRepository.save(existingUser), UserOutputDTO.class);
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
        user.setAddress(modelMapper.map(input, Address.class));
        return modelMapper.map(userRepository.save(user), UserOutputDTO.class);
    }

    protected User findById(String id) {
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