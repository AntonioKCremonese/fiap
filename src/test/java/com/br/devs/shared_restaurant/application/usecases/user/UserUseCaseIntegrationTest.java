package com.br.devs.shared_restaurant.application.usecases.user;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import com.br.devs.shared_restaurant.core.exceptions.UserValidationException;
import com.br.devs.shared_restaurant.core.interfaces.IUserUseCase;
import com.br.devs.shared_restaurant.utils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserUseCaseIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private IUserUseCase userUseCase;

    @Test
    void shouldCreateUserSuccessfully() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("João Silva")
                .withMail("joao@example.com")
                .withUserType(UserTypeEnum.CLIENT)
                .buildCreateDTO();

        UserOutputDTO result = userUseCase.createUser(userCreateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("João Silva");
        assertThat(result.getMail()).isEqualTo("joao@example.com");
        assertThat(result.getUserType()).isEqualTo(UserTypeEnum.CLIENT);
    }

    @Test
    void shouldFindUserById() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Maria Santos")
                .withMail("maria@example.com")
                .buildCreateDTO();

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        UserOutputDTO foundUser = userUseCase.getUserById(createdUser.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(createdUser.getId());
        assertThat(foundUser.getName()).isEqualTo("Maria Santos");
        assertThat(foundUser.getMail()).isEqualTo("maria@example.com");
    }

    @Test
    void shouldCreateOwnerUser() {
        UserCreateDTO ownerDTO = TestDataBuilder.defaultUser()
                .withName("Carlos Dono")
                .withMail("carlos@restaurant.com")
                .withUserType(UserTypeEnum.OWNER)
                .buildCreateDTO();

        UserOutputDTO result = userUseCase.createUser(ownerDTO);

        assertThat(result).isNotNull();
        assertThat(result.getUserType()).isEqualTo(UserTypeEnum.OWNER);
        assertThat(result.getName()).isEqualTo("Carlos Dono");
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Pedro Santos")
                .withMail("pedro@example.com")
                .withLogin("pedro123")
                .buildCreateDTO();

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        UserUpdateDTO updateDTO = TestDataBuilder.defaultUser()
                .withName("Pedro Santos Updated")
                .withMail("pedro.updated@example.com")
                .withLogin("pedro_updated")
                .withUserType(UserTypeEnum.OWNER)
                .buildUpdateDTO();

        UserOutputDTO updatedUser = userUseCase.updateUser(createdUser.getId(), updateDTO);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(updatedUser.getName()).isEqualTo("Pedro Santos Updated");
        assertThat(updatedUser.getMail()).isEqualTo("pedro.updated@example.com");
        assertThat(updatedUser.getLogin()).isEqualTo("pedro_updated");
        assertThat(updatedUser.getUserType()).isEqualTo(UserTypeEnum.OWNER);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Ana Costa")
                .withMail("ana@example.com")
                .buildCreateDTO();

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        userUseCase.deleteUser(createdUser.getId());

        // Then - Should throw exception when trying to find deleted user
        assertThatThrownBy(() -> userUseCase.getUserById(createdUser.getId()))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldUpdatePasswordSuccessfully() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Lucas Silva")
                .withMail("lucas@example.com")
                .withPassword("OldPass123")
                .buildCreateDTO();

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        PasswordUpdateDTO passwordDTO = TestDataBuilder.defaultPasswordUpdateDTO()
                .withNewPassword("NewPass123")
                .withPasswordConfirmation("NewPass123")
                .withCurrentPassword("OldPass123")
                .build();

        // When & Then - Should not throw exception
        userUseCase.updatePassword(createdUser.getId(), passwordDTO);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPasswordWithWrongCurrentPassword() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Carla Lima")
                .withMail("carla@example.com")
                .withPassword("CorrectPass123")
                .buildCreateDTO();

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        PasswordUpdateDTO passwordDTO = TestDataBuilder.defaultPasswordUpdateDTO()
                .withNewPassword("NewPass123")
                .withPasswordConfirmation("NewPass123")
                .withCurrentPassword("WrongPass123")
                .build();

        assertThatThrownBy(() -> userUseCase.updatePassword(createdUser.getId(), passwordDTO))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenPasswordConfirmationDoesNotMatch() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Roberto Silva")
                .withMail("roberto@example.com")
                .withPassword("CurrentPass123")
                .buildCreateDTO();

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        PasswordUpdateDTO passwordDTO = TestDataBuilder.defaultPasswordUpdateDTO()
                .withNewPassword("NewPass123")
                .withPasswordConfirmation("DifferentPass123")
                .withCurrentPassword("CurrentPass123")
                .build();

        assertThatThrownBy(() -> userUseCase.updatePassword(createdUser.getId(), passwordDTO))
                .isInstanceOf(UserValidationException.class);
    }

    @Test
    void shouldUpdateUserAddressSuccessfully() {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Fernanda Oliveira")
                .withMail("fernanda@example.com")
                .buildCreateDTO();

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        AddressInputDTO newAddressDTO = TestDataBuilder.defaultAddressInputDTO()
                .withStreet("Rua Nova")
                .withNumber(456)
                .withComplement("Casa 2")
                .withNeighborhood("Centro")
                .withCity("São Paulo")
                .withState("SP")
                .withPostalCode("01234567")
                .build();

        UserOutputDTO updatedUser = userUseCase.updateUserAddress(createdUser.getId(), newAddressDTO);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(updatedUser.getAddress()).isNotNull();
        assertThat(updatedUser.getAddress().getStreet()).isEqualTo("Rua Nova");
        assertThat(updatedUser.getAddress().getNumber()).isEqualTo(456);
        assertThat(updatedUser.getAddress().getComplement()).isEqualTo("Casa 2");
        assertThat(updatedUser.getAddress().getNeighborhood()).isEqualTo("Centro");
        assertThat(updatedUser.getAddress().getCity()).isEqualTo("São Paulo");
        assertThat(updatedUser.getAddress().getState()).isEqualTo("SP");
        assertThat(updatedUser.getAddress().getPostalCode()).isEqualTo("01234567");
    }

    @Test
    void shouldCreateUserAddressWhenUpdatingUserWithoutAddress() {
        // Given - User without address
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withName("Monica Santos")
                .withMail("monica@example.com")
                .buildCreateDTO();
        userCreateDTO.setAddress(null); // Remove address

        UserOutputDTO createdUser = userUseCase.createUser(userCreateDTO);

        AddressInputDTO addressDTO = TestDataBuilder.defaultAddressInputDTO()
                .withStreet("Rua Primeira")
                .withNumber(100)
                .withCity("Rio de Janeiro")
                .withState("RJ")
                .build();

        UserOutputDTO updatedUser = userUseCase.updateUserAddress(createdUser.getId(), addressDTO);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getAddress()).isNotNull();
        assertThat(updatedUser.getAddress().getStreet()).isEqualTo("Rua Primeira");
        assertThat(updatedUser.getAddress().getNumber()).isEqualTo(100);
        assertThat(updatedUser.getAddress().getCity()).isEqualTo("Rio de Janeiro");
        assertThat(updatedUser.getAddress().getState()).isEqualTo("RJ");
    }
}