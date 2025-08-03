package com.br.devs.shared_restaurant.application.usecases;

import com.br.devs.shared_restaurant.core.dto.input.AuthDTO;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
import com.br.devs.shared_restaurant.core.interfaces.IAuthUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUseCaseImpl implements IAuthUseCase {

    private final IUserGateway userGateway;

    AuthUseCaseImpl(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValidPassword(AuthDTO authDTO) {
        User user = userGateway.findUserByLogin(authDTO.login()).orElseThrow(UserValidationException::userNotFoundException);
        return user.getPassword().equals(authDTO.password());
    }
}
