package com.br.devs.shared_restaurant.old_service;

import com.br.devs.shared_restaurant.core.dto.input.AuthDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public boolean isValidPassword(AuthDTO authDTO) {
        var user = userService.findByLogin(authDTO.login());
        return user.getPassword().equals(authDTO.password());
    }
}