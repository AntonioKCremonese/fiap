package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.exception.UserAlreadyExistsException;
import com.br.devs.shared_restaurant.model.User;
import com.br.devs.shared_restaurant.dto.UserInput;
import com.br.devs.shared_restaurant.dto.UserOutput;
import com.br.devs.shared_restaurant.exception.UserNotFoundException;
import com.br.devs.shared_restaurant.mapper.UserMapper;
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
    public UserOutput getUserById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::fromEntity)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserOutput createUser(UserInput userInput) {
        userAlreadyExists(userInput.mail(), userInput.login());
        User user = UserMapper.toEntity(userInput);
        user.setPassword(encodePassword(user.getPassword()));
        return UserMapper.fromEntity(userRepository.save(user));
    }

    @Transactional
    public void updateUser(UserInput userInput) {
        User user = UserMapper.toEntity(userInput);
        UserMapper.fromEntity(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    private void userAlreadyExists(String mail, String login) {
        userRepository.findByMailOrLogin(mail, login).ifPresent(user -> {
            throw new UserAlreadyExistsException();
        });
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}