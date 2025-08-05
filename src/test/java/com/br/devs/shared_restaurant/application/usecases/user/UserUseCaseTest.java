package com.br.devs.shared_restaurant.application.usecases.user;

import com.br.devs.shared_restaurant.application.usecases.UserUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
import com.br.devs.shared_restaurant.core.presenters.UserPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @Mock
    private IUserGateway userGateway;

    @Test
    void shouldGetUserByIdSuccessfully() {
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail.com")
                .build();
        when(userGateway.findUserById(any())).thenReturn(user);

        var userFound = userUseCase.getUserById("12345");

        assertThat(userFound).isNotNull();
        assertThat(userFound.getId()).isEqualTo("12345");
    }

    @Test
    void shouldCreateUserSuccessfully() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd1234");

        var user = UserPresenter.toEntity(input);
        user.setId("12345");

        when(userGateway.save(any())).thenReturn(user);

        var userCreated = userUseCase.createUser(input);

        assertThat(userCreated).isNotNull();
    }

    @Test
    void shouldCreateUserWithAddressSuccessfully() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd1234");

        var address = new AddressInputDTO();
        address.setNumber(1);
        address.setCity("SBO");
        address.setCountry("BRAZIL");
        input.setAddress(address);

        var user = UserPresenter.toEntity(input);
        user.setId("12345");

        when(userGateway.save(any())).thenReturn(user);

        var userCreated = userUseCase.createUser(input);

        assertThat(userCreated).isNotNull();
        assertThat(userCreated.getAddress()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenPasswordConfirmationIsInvalid() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd123");

        assertThatThrownBy(() -> userUseCase.createUser(input))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenLoginAlreadyExists() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd1234");

        when(userGateway.findUserByLogin(input.getLogin()))
                .thenReturn(Optional.of(UserPresenter.toEntity(input)));

        assertThatThrownBy(() -> userUseCase.createUser(input))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenMailAlreadyExists() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd1234");

        when(userGateway.findUserByMail(input.getMail()))
                .thenReturn(Optional.of(UserPresenter.toEntity(input)));

        assertThatThrownBy(() -> userUseCase.createUser(input))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        var input = new UserUpdateDTO();
        input.setName("John Updated");
        input.setMail("mail_Updated@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");

        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com")
                .userType(UserTypeEnum.CLIENT)
                .login("joe_doe")
                .build();

        when(userGateway.findUserById(any())).thenReturn(user);
        when(userGateway.save(any())).thenReturn(user);

        var updatedUser = userUseCase.updateUser("12345", input);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("John Updated");
        assertThat(updatedUser.getMail()).isEqualTo("mail_Updated@joedoe.com");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingUserWithExistingLogin() {
        var input = new UserUpdateDTO();
        input.setName("John Updated");
        input.setMail("mail_Updated@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");

        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com")
                .userType(UserTypeEnum.CLIENT)
                .login("joe_doe")
                .build();

        when(userGateway.findUserById(any())).thenReturn(user);
        when(userGateway.findUserByLogin(any())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userUseCase.updateUser("12345", input))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingUserWithExistingMail() {
        var input = new UserUpdateDTO();
        input.setName("John Updated");
        input.setMail("mail_Updated@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");

        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail_Updated@joedoe.com")
                .userType(UserTypeEnum.CLIENT)
                .login("joe_doe")
                .build();

        when(userGateway.findUserById(any())).thenReturn(user);
        when(userGateway.findUserByMail(any())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userUseCase.updateUser("12345", input))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com")
                .build();

        when(userGateway.findUserById(any())).thenReturn(user);

        userUseCase.deleteUser("12345");

        verify(userGateway, times(1)).deleteUserById(user.getId());
    }

    @Test
    void shouldUpdateUserPasswordSuccessfully() {
        var input = new PasswordUpdateDTO("Newpass1234", "Newpass1234", "Abcd1234");
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com")
                .password("Abcd1234")
                .build();

        when(userGateway.findUserById(any())).thenReturn(user);
        when(userGateway.save(any())).thenReturn(user);

        userUseCase.updatePassword("12345", input);

        assertThat(user.getPassword()).isEqualTo("Newpass1234");
    }

    @Test
    void shouldThrowExceptionWhenPasswordConfirmationDoesNotMatch() {
        var input = new PasswordUpdateDTO("Newpass1234", "Newpass165", "Abcd1234");

        assertThatThrownBy(() -> userUseCase.updatePassword("12345", input))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenCurrentPasswordIsInvalid() {
        var input = new PasswordUpdateDTO("Newpass1234", "Newpass1234", "Abcd1234");
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com")
                .password("Abcd1234678")
                .build();

        when(userGateway.findUserById(any())).thenReturn(user);

        assertThatThrownBy(() -> userUseCase.updatePassword("12345", input))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldUpdateUserAddressSuccessfully() {
        var input = new AddressInputDTO();
        input.setStreet("Main St");
        input.setNumber(123);
        input.setComplement("Apt 4B");
        input.setNeighborhood("Downtown");
        input.setCity("Springfield");
        input.setState("IL");
        input.setCountry("USA");
        input.setPostalCode("62701");
        input.setReference("Near the park");

        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail.com")
                .build();

        when(userGateway.findUserById(any())).thenReturn(user);
        when(userGateway.save(any())).thenReturn(user);

        var updatedUserAddress = userUseCase.updateUserAddress("12345", input);

        assertThat(updatedUserAddress).isNotNull();
        assertThat(updatedUserAddress.getAddress()).isNotNull();
    }
}