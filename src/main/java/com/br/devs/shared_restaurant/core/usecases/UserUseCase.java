package com.br.devs.shared_restaurant.core.usecases;

import com.br.devs.shared_restaurant.core.entities.Address;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.interfaces.IUserGateway;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;

public class UserUseCase {

    private final IUserGateway userGateway;

    private UserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static UserUseCase create(IUserGateway userGateway) {
        return new UserUseCase(userGateway);
    }

    public User createUser(UserCreateDTO userCreateDTO) {
        User.validatePasswordConfirmation(userCreateDTO.getPassword(), userCreateDTO.getPasswordConfirmation());
        userGateway.findByLogin(userCreateDTO.getLogin()).ifPresent(user -> User.validateLoginAlreadyExists(user.getLogin(), userCreateDTO.getLogin()));
        userGateway.findByMail(userCreateDTO.getMail()).ifPresent(user -> User.validateMailAlreadyExists(user.getMail(), userCreateDTO.getMail()));
        Address address = Address.create(null, userCreateDTO.getAddress());
        User user = User.create(null, userCreateDTO, address);
        return userGateway.save(user);
    }

    public UserOutputDTO updateUser() {
        return null;
    }

    public UserOutputDTO deleteUser() {
        return null;
    }

    public UserOutputDTO updatePassword() {
        return null;
    }

    public UserOutputDTO updateAddress() {
        return null;
    }
}
