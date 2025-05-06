package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.dto.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.dto.UserCreateDTO;
import com.br.devs.shared_restaurant.dto.UserOutputDTO;
import com.br.devs.shared_restaurant.dto.UserUpdateDTO;
import com.br.devs.shared_restaurant.exception.UserAlreadyExistsException;
import com.br.devs.shared_restaurant.exception.UserNotFoundException;
import com.br.devs.shared_restaurant.exception.UserValidationException;
import com.br.devs.shared_restaurant.mapper.UserMapper;
import com.br.devs.shared_restaurant.model.User;
import com.br.devs.shared_restaurant.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                throw new UserAlreadyExistsException("Já existe um usuário cadastrado com este login.");
            }
        });

        userRepository.findByMail(input.mail()).ifPresent(user -> {
            if (user.getMail().equals(input.mail())) {
                throw new UserAlreadyExistsException("Já existe um usuário cadastrado com este e-mail.");
            }
        });

        User user = UserMapper.toEntity(input);
        user.setPassword(encodePassword(user.getPassword()));
        return UserMapper.fromEntity(userRepository.save(user));
    }

    @Transactional
    public UserOutputDTO updateUser(String id, UserUpdateDTO input) {
        User existingUser = findById(id);

        userRepository.findByLogin(input.login()).ifPresent(user -> {
            if (user.getLogin().equals(input.login()) && !existingUser.equals(user)) {
                throw new UserAlreadyExistsException("Já existe um usuário cadastrado com este login.");
            }
        });

        userRepository.findByMail(input.mail()).ifPresent(user -> {
            if (user.getMail().equals(input.mail()) && !existingUser.equals(user)) {
                throw new UserAlreadyExistsException("Já existe um usuário cadastrado com este e-mail.");
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

        if (!passwordEncoder.matches(input.currentPassword(), user.getPassword())) {
            throw UserValidationException.existingPasswordNotValid();
        }

        user.updatePassword(encodePassword(input.newPassword()));
        userRepository.save(user);
    }

    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private static void validatePasswordConfirmation(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw UserValidationException.confirmationPasswordNotValid();
        }
    }
}