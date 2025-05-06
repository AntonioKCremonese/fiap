package com.br.devs.shared_restaurant.controller;

import com.br.devs.shared_restaurant.dto.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.dto.UserCreateDTO;
import com.br.devs.shared_restaurant.dto.UserOutputDTO;
import com.br.devs.shared_restaurant.dto.UserUpdateDTO;
import com.br.devs.shared_restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserOutputDTO getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDTO createUser(@RequestBody @Valid UserCreateDTO input) {
        return userService.createUser(input);
    }

    @PutMapping("/{userId}")
    public UserOutputDTO updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateDTO input) {
        return userService.updateUser(userId, input);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}/password-update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String userId, @RequestBody @Valid PasswordUpdateDTO input) {
        userService.updatePassword(userId, input);
    }
}