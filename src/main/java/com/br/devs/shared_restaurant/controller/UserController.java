package com.br.devs.shared_restaurant.controller;

import com.br.devs.shared_restaurant.dto.UserInput;
import com.br.devs.shared_restaurant.dto.UserOutput;
import com.br.devs.shared_restaurant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return userService.createOrUpdateUser(input);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody UserInput input) { userService.createOrUpdateUser(input); }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) { userService.deleteUser(userId); }

}