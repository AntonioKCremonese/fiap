package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.interfaces.IUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final IUserUseCase userUseCase;

    public UserController(IUserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping("/{userId}")
    public UserOutputDTO getUserById(@PathVariable String userId) {
        return userUseCase.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDTO createUser(@RequestBody @Valid UserCreateDTO input) {
        return userUseCase.createUser(input);
    }

    @PutMapping("/{userId}")
    public UserOutputDTO updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateDTO input) {
        return userUseCase.updateUser(userId, input);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        userUseCase.deleteUser(userId);
    }

    @PutMapping("/{userId}/password-update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String userId, @RequestBody @Valid PasswordUpdateDTO input) {
        userUseCase.updatePassword(userId, input);
    }

    @PutMapping("/{userId}/address")
    public UserOutputDTO updateUserAddress(@PathVariable String userId, @RequestBody @Valid AddressInputDTO input) {
        return userUseCase.updateUserAddress(userId, input);
    }
}