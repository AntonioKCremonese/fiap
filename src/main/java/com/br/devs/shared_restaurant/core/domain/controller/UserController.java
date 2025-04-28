package com.br.devs.shared_restaurant.core.domain.controller;

import com.br.devs.shared_restaurant.core.domain.dto.UserInput;
import com.br.devs.shared_restaurant.core.domain.dto.UserOutput;
import com.br.devs.shared_restaurant.core.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserOutput getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput createUser(@RequestBody UserInput input) {
        return userService.createUser(input);
    }
}