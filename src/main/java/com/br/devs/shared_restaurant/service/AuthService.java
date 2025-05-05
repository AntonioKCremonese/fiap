package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.dto.AuthDTO;
import com.br.devs.shared_restaurant.exception.UserValidationException;
import com.br.devs.shared_restaurant.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(AuthDTO authDTO) {
        var user = this.userRepository.findByLogin(authDTO.login())
                .orElseThrow(UserValidationException::userNotFoundException);

        return passwordEncoder.matches(authDTO.password(), user.getPassword());
    }
}
