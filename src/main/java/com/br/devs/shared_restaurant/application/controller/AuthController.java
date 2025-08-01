package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.core.dto.input.AuthDTO;
import com.br.devs.shared_restaurant.core.interfaces.IAuthUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthUseCase authUseCase;

    public AuthController(IAuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        if (!authUseCase.isValidPassword(authDTO)) {
            return ResponseEntity.badRequest().body("Senha inválida.");
        }

        return ResponseEntity.ok("Login efetuado com sucesso.");
    }
}