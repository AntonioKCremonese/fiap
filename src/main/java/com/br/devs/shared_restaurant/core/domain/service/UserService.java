package com.br.devs.shared_restaurant.core.domain.service;

import com.br.devs.shared_restaurant.core.domain.model.User;
import com.br.devs.shared_restaurant.core.domain.dto.UserInput;
import com.br.devs.shared_restaurant.core.domain.dto.UserOutput;
import com.br.devs.shared_restaurant.core.domain.exception.UserNotFoundException;
import com.br.devs.shared_restaurant.core.domain.mapper.UserMapper;
import com.br.devs.shared_restaurant.core.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserOutput getUserById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::fromEntity)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserOutput createUser(UserInput userInput) {
        User user = UserMapper.toEntity(userInput);
        return UserMapper.fromEntity(userRepository.save(user));
    }
}