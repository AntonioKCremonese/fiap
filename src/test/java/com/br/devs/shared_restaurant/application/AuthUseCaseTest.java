package com.br.devs.shared_restaurant.application;

import com.br.devs.shared_restaurant.application.usecases.AuthUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.AuthDTO;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AuthUseCaseTest {

    @InjectMocks
    private AuthUseCaseImpl authUseCase;

    @Mock
    private IUserGateway userGateway;

    @Test
    public void testLogin() {
        var user = User.builder().login("login").password("password").build();

        Mockito.when(userGateway.findUserByLogin(Mockito.anyString())).thenReturn(Optional.of(user));

        var isValid = authUseCase.isValidPassword(new AuthDTO("login", "password"));
        Assert.assertTrue(isValid);
    }

    @Test
    public void testLoginWithInvalidPassword() {
        var user = User.builder().login("login").password("password").build();

        Mockito.when(userGateway.findUserByLogin(Mockito.anyString())).thenReturn(Optional.of(user));

        var isValid = authUseCase.isValidPassword(new AuthDTO("login", "wrongPassword"));
        Assert.assertFalse(isValid);
    }

    @Test
    public void testLoginWithNonExistentUser() {
        Mockito.when(userGateway.findUserByLogin(Mockito.anyString())).thenReturn(Optional.empty());

        try {
            authUseCase.isValidPassword(new AuthDTO("nonExistentUser", "password"));
            Assert.fail("Expected UserValidationException to be thrown");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UserValidationException);
        }
    }
}
