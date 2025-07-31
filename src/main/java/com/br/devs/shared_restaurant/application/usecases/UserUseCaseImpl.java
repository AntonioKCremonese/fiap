package com.br.devs.shared_restaurant.application.usecases;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.Address;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.interfaces.IUserGateway;
import com.br.devs.shared_restaurant.core.interfaces.IUserUseCase;
import com.br.devs.shared_restaurant.core.presenters.UserPresenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserUseCaseImpl implements IUserUseCase {

    private final IUserGateway userGateway;

    UserUseCaseImpl(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    @Transactional
    public UserOutputDTO getUserById(String id) {
        User user = userGateway.findUserById(id).orElseThrow(UserValidationException::userNotFoundException);
        return UserPresenter.toDTO(user);
    }

    @Override
    @Transactional
    public UserOutputDTO createUser(UserCreateDTO userCreateDTO) {
        User.validatePasswordConfirmation(userCreateDTO.getPassword(), userCreateDTO.getPasswordConfirmation());
        userGateway.findUserByLogin(userCreateDTO.getLogin()).ifPresent(user -> User.validateLoginAlreadyExists(user.getLogin(), userCreateDTO.getLogin()));
        userGateway.findUserByMail(userCreateDTO.getMail()).ifPresent(user -> User.validateMailAlreadyExists(user.getMail(), userCreateDTO.getMail()));
        Address address = Address.create(null, userCreateDTO.getAddress());
        User user = User.create(null, userCreateDTO, address);
        return UserPresenter.toDTO(userGateway.save(user));
    }

    @Override
    @Transactional
    public UserOutputDTO updateUser(String id, UserUpdateDTO input) {
        User existingUser = userGateway.findUserById(id).orElseThrow(UserValidationException::userNotFoundException);
        userGateway.findUserByLogin(input.getLogin()).ifPresent(user -> User.validateLoginAlreadyExists(user.getLogin(), input.getLogin()));
        userGateway.findUserByMail(input.getMail()).ifPresent(user -> User.validateMailAlreadyExists(user.getMail(), input.getMail()));
        User userToUpdate = User.update(existingUser, input);
        return UserPresenter.toDTO(userGateway.save(userToUpdate));
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        userGateway.findUserById(userId).orElseThrow(UserValidationException::userNotFoundException);
        userGateway.deleteUserById(userId);
    }

    @Override
    @Transactional
    public void updatePassword(String userId, PasswordUpdateDTO input) {
        User.validatePasswordConfirmation(input.newPassword(), input.passwordConfirmation());
        User existingUser = userGateway.findUserById(userId).orElseThrow(UserValidationException::userNotFoundException);
        User.validateCurrentPassword(existingUser.getPassword(), input.currentPassword());
        User user = User.updatePassword(existingUser, input.newPassword());
        userGateway.save(user);
    }

    @Override
    @Transactional
    public UserOutputDTO updateUserAddress(String userId, AddressInputDTO input) {
        User existingUser = userGateway.findUserById(userId).orElseThrow(UserValidationException::userNotFoundException);
        User userToUpdate = User.updateAddress(existingUser, input);
        return UserPresenter.toDTO(userGateway.save(userToUpdate));
    }
}
