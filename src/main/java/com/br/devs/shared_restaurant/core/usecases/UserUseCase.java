package com.br.devs.shared_restaurant.core.usecases;

import com.br.devs.shared_restaurant.core.entities.Address;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.interfaces.IUserGateway;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserUseCase {

    private final IUserGateway userGateway;

    private UserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static UserUseCase create(IUserGateway userGateway) {
        return new UserUseCase(userGateway);
    }

    public User createUser(UserCreateDTO userCreateDTO) {
        log.info("Criando ..");
        User.validatePasswordConfirmation(userCreateDTO.getPassword(), userCreateDTO.getPasswordConfirmation());
        log.info("Passou validação password ..");
        userGateway.findByLogin(userCreateDTO.getLogin()).ifPresent(user -> User.validateLoginAlreadyExists(user.getLogin(), userCreateDTO.getLogin()));
        log.info("Passou validação login ..");
        userGateway.findByMail(userCreateDTO.getMail()).ifPresent(user -> User.validateMailAlreadyExists(user.getMail(), userCreateDTO.getMail()));
        log.info("Passou validação mail ..");
        Address address = Address.create(null, userCreateDTO.getAddress());
        log.info("Criou endereço ..");
        User user = User.create(null, userCreateDTO, address);
        log.info("Salvando User ..");
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
