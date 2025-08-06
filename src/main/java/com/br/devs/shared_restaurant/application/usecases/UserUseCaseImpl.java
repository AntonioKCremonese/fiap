package com.br.devs.shared_restaurant.application.usecases;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
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
        User user = userGateway.findUserById(id);
        return UserPresenter.toDTO(user);
    }

    @Override
    @Transactional
    public UserOutputDTO createUser(UserCreateDTO userCreateDTO) {
        User.validatePasswordConfirmation(userCreateDTO.getPassword(), userCreateDTO.getPasswordConfirmation());
        userGateway.findUserByLogin(userCreateDTO.getLogin()).ifPresent(user -> User.validateLoginAlreadyExists(user.getLogin(), userCreateDTO.getLogin()));
        userGateway.findUserByMail(userCreateDTO.getMail()).ifPresent(user -> User.validateMailAlreadyExists(user.getMail(), userCreateDTO.getMail()));
        User userToCreate = UserPresenter.toEntity(userCreateDTO);
        return UserPresenter.toDTO(userGateway.save(userToCreate));
    }

    @Override
    @Transactional
    public UserOutputDTO updateUser(String id, UserUpdateDTO input) {
        User existingUser = userGateway.findUserById(id);
        User.update(existingUser, input);
        return UserPresenter.toDTO(userGateway.save(existingUser));
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        userGateway.deleteUserById(userGateway.findUserById(userId).getId());
    }

    @Override
    @Transactional
    public void updatePassword(String userId, PasswordUpdateDTO input) {
        User.validatePasswordConfirmation(input.newPassword(), input.passwordConfirmation());
        User existingUser = userGateway.findUserById(userId);
        User.validateCurrentPassword(existingUser.getPassword(), input.currentPassword());
        User.updatePassword(existingUser, input.newPassword());
        userGateway.save(existingUser);
    }

    @Override
    @Transactional
    public UserOutputDTO updateUserAddress(String userId, AddressInputDTO input) {
        User existingUser = userGateway.findUserById(userId);
        User.updateAddress(existingUser, input);
        return UserPresenter.toDTO(userGateway.save(existingUser));
    }
}