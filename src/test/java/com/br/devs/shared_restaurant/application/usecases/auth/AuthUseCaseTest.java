package com.br.devs.shared_restaurant.application.usecases.auth;

import com.br.devs.shared_restaurant.application.usecases.AuthUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.AuthDTO;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @InjectMocks
    private AuthUseCaseImpl authUseCase;

    @Mock
    private IUserGateway userGateway;

    @Test
    void shouldLoginWithValidCredentials() {
        var user = User.builder()
                .login("login")
                .password("password")
                .build();

        when(userGateway.findUserByLogin(anyString())).thenReturn(Optional.of(user));

        var isValid = authUseCase.isValidPassword(new AuthDTO("login", "password"));

        assertThat(isValid).isTrue();
    }

    @Test
    void shouldRejectLoginWithInvalidPassword() {
        var user = User.builder()
                .login("login")
                .password("password")
                .build();

        when(userGateway.findUserByLogin(anyString())).thenReturn(Optional.of(user));

        var isValid = authUseCase.isValidPassword(new AuthDTO("login", "wrongPassword"));

        assertThat(isValid).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userGateway.findUserByLogin(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                authUseCase.isValidPassword(new AuthDTO("nonExistentUser", "password"))
        ).isInstanceOf(UserValidationException.class);
    }
}