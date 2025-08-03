package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;

public interface IUserUseCase {
    UserOutputDTO getUserById(String id);
    UserOutputDTO createUser(UserCreateDTO userCreateDTO);
    UserOutputDTO updateUser(String id, UserUpdateDTO input);
    void deleteUser(String userId);
    void updatePassword(String id, PasswordUpdateDTO input);
    UserOutputDTO updateUserAddress(String userId, AddressInputDTO input);
}
