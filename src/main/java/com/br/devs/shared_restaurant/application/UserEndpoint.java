package com.br.devs.shared_restaurant.application;

import com.br.devs.shared_restaurant.core.controller.UserController;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.interfaces.IDataSource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserEndpoint {

    private final UserController userController;
    private final IDataSource dataSource;


    public UserEndpoint(IDataSource dataSource) {
        this.dataSource = dataSource;
        this.userController = UserController.create(this.dataSource);
    }

    @GetMapping("/{userId}")
    public UserOutputDTO getUserById(@PathVariable String userId) {
//        return userService.getUserById(userId);
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDTO createUser(@RequestBody @Valid UserCreateDTO input) {
        log.info("Chamando endpoint de criar usuário: {}", input.getName());
        return userController.createUser(input);
    }

    @PutMapping("/{userId}")
    public UserOutputDTO updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateDTO input) {
//        return userService.updateUser(userId, input);
        return null;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
//        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}/password-update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String userId, @RequestBody @Valid PasswordUpdateDTO input) {
//        userService.updatePassword(userId, input);
    }

    @PutMapping("/{userId}/address")
    public UserOutputDTO updateUserAddress(@PathVariable String userId, @RequestBody @Valid AddressInputDTO input) {
//        return userService.updateUserAddress(userId, input);
        return null;
    }
}