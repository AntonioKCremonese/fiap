package com.br.devs.shared_restaurant.controller;

import com.br.devs.shared_restaurant.dto.AuthDTO;
import com.br.devs.shared_restaurant.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        var isLoginValid = this.authService.login(authDTO);
        if (!isLoginValid) {
            return ResponseEntity.badRequest().body("Falha ao logar usuário");
        }
        return ResponseEntity.ok("Login efetuado com sucesso");
    }
}
