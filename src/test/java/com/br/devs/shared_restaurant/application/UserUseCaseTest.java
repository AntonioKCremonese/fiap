package com.br.devs.shared_restaurant.application;

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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UserUseCaseTest {

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @Mock
    private IUserGateway userGateway;

    @Test
    public void testGetUserByIdSuccessFully() {
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail.com").build();
        Mockito.when(userGateway.findUserById(any())).thenReturn(user);

        var userFounded = userUseCase.getUserById("12345");

        Assert.assertNotNull(userFounded);
        Assert.assertEquals("12345", userFounded.getId());
    }

    @Test
    public void testCreateUserSuccessFully() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd1234");

        var user = UserPresenter.toEntity(input);
        user.setId("12345");

        Mockito.when(userGateway.save(any())).thenReturn(user);
        var userCreated = userUseCase.createUser(input);

        Assert.assertNotNull(userCreated);
    }

    @Test
    public void testCreateUserWithAddressSuccessFully() {
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
        address.setCountry("BRAZEL");
        input.setAddress(address);

        var user = UserPresenter.toEntity(input);
        user.setId("12345");

        Mockito.when(userGateway.save(any())).thenReturn(user);
        var userCreated = userUseCase.createUser(input);

        Assert.assertNotNull(userCreated);
        Assert.assertNotNull(userCreated.getAddress());
    }

    @Test(expected = UserValidationException.class)
    public void testCreateUserWithInvalidConfirmationPassword() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd123");

        userUseCase.createUser(input);
    }

    @Test(expected = UserValidationException.class)
    public void testCreateUserWithAlreadyLoginCreated() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd1234");

        Mockito.when(userGateway.findUserByLogin(input.getLogin())).thenReturn(Optional.of(UserPresenter.toEntity(input)));

        userUseCase.createUser(input);
    }

    @Test(expected = UserValidationException.class)
    public void testCreateUserWithAlreadyMailCreated() {
        var input = new UserCreateDTO();
        input.setName("John Doe");
        input.setMail("mail@joedoe.com");
        input.setUserType(UserTypeEnum.CLIENT);
        input.setLogin("joe_doe");
        input.setPassword("Abcd1234");
        input.setPasswordConfirmation("Abcd1234");

        Mockito.when(userGateway.findUserByMail(input.getMail())).thenReturn(Optional.of(UserPresenter.toEntity(input)));

        userUseCase.createUser(input);
    }

    @Test
    public void testUpdateUserSuccessFully() {
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

        Mockito.when(userGateway.findUserById(any())).thenReturn(user);
        Mockito.when(userGateway.save(any())).thenReturn(user);
        var updatedUser = userUseCase.updateUser("12345", input);

        Assert.assertNotNull(updatedUser);
        Assert.assertEquals("John Updated" , updatedUser.getName());
        Assert.assertEquals("mail_Updated@joedoe.com" , updatedUser.getMail());
    }

    @Test(expected = UserValidationException.class)
    public void testUpdateUserWithAlreadyLogin() {
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

        Mockito.when(userGateway.findUserById(any())).thenReturn(user);
        Mockito.when(userGateway.findUserByLogin(any())).thenReturn(Optional.of(user));
        userUseCase.updateUser("12345", input);
    }

    @Test(expected = UserValidationException.class)
    public void testUpdateUserWithAlreadyMail() {
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

        Mockito.when(userGateway.findUserById(any())).thenReturn(user);
        Mockito.when(userGateway.findUserByMail(any())).thenReturn(Optional.of(user));
        userUseCase.updateUser("12345", input);
    }

    @Test
    public void testDeleteUserSuccessFully() {
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com").build();

        Mockito.when(userGateway.findUserById(any())).thenReturn(user);
        userUseCase.deleteUser("12345");
        Mockito.verify(userGateway, Mockito.times(1)).deleteUserById(user.getId());
    }

    @Test
    public void testUpdateUserPasswordSuccessFully() {
        var input = new PasswordUpdateDTO("Newpass1234", "Newpass1234", "Abcd1234");
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com")
                .password("Abcd1234")
                .build();
        Mockito.when(userGateway.findUserById(any())).thenReturn(user);
        Mockito.when(userGateway.save(any())).thenReturn(user);
        userUseCase.updatePassword("12345", input);
        Assert.assertEquals("Newpass1234", user.getPassword());
    }

    @Test(expected = UserValidationException.class)
    public void testUpdateUserPasswordWithInvalidPasswordConfirmation() {
        var input = new PasswordUpdateDTO("Newpass1234", "Newpass165", "Abcd1234");
        userUseCase.updatePassword("12345", input);
    }

    @Test(expected = UserValidationException.class)
    public void testUpdateUserPasswordWithInvalidCurrentPassword() {
        var input = new PasswordUpdateDTO("Newpass1234", "Newpass1234", "Abcd1234");
        var user = User.builder()
                .id("12345")
                .name("John Doe")
                .mail("mail@joedoe.com")
                .password("Abcd1234678")
                .build();
        Mockito.when(userGateway.findUserById(any())).thenReturn(user);
        userUseCase.updatePassword("12345", input);
    }

    @Test
    public void testUpdateUserAddressSuccessFully() {
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
                .mail("mail.com").build();

        Mockito.when(userGateway.findUserById(any())).thenReturn(user);
        Mockito.when(userGateway.save(any())).thenReturn(user);

        var updatedUserAddress = userUseCase.updateUserAddress("12345", input);

        Assert.assertNotNull(updatedUserAddress);
        Assert.assertNotNull(updatedUserAddress.getAddress());
    }
}
