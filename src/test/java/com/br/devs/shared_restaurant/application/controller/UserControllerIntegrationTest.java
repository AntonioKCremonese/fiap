package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UserControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.mail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.login").value("johndoe"))
                .andExpect(jsonPath("$.userType").value("CLIENT"))
                .andExpect(jsonPath("$.address").exists())
                .andExpect(jsonPath("$.address.street").value("Main Street"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setName("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setName(null);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameExceedsMaxLength() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setName("A".repeat(126));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMailIsBlank() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setMail("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMailIsInvalid() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setMail("invalid-email");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMailExceedsMaxLength() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setMail("a".repeat(190) + "@email.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenLoginIsBlank() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setLogin("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenLoginExceedsMaxLength() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setLogin("A".repeat(51));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsBlank() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setPassword("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsTooShort() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setPassword("Pass1");
        inputDTO.setPasswordConfirmation("Pass1");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordDoesNotMatchPattern() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setPassword("password123");
        inputDTO.setPasswordConfirmation("password123");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordConfirmationIsBlank() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setPasswordConfirmation("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordConfirmationDoesNotMatch() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setPasswordConfirmation("DifferentPassword123");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenUserTypeIsNull() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();
        inputDTO.setUserType(null);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetUserByIdSuccessfully() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        mockMvc.perform(get("/users/{userId}", createdUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUser.getId()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.mail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.login").value("johndoe"))
                .andExpect(jsonPath("$.userType").value("CLIENT"));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/{userId}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        UserUpdateDTO updateDTO = createValidUserUpdateDTO();
        updateDTO.setName("Jane Smith");
        updateDTO.setMail("jane.smith@example.com");
        updateDTO.setLogin("janesmith");
        updateDTO.setUserType(UserTypeEnum.OWNER);

        mockMvc.perform(put("/users/{userId}", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUser.getId()))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.mail").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.login").value("janesmith"))
                .andExpect(jsonPath("$.userType").value("OWNER"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateNameIsBlank() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        UserUpdateDTO updateDTO = createValidUserUpdateDTO();
        updateDTO.setName("");

        mockMvc.perform(put("/users/{userId}", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentUser() throws Exception {
        UserUpdateDTO updateDTO = createValidUserUpdateDTO();
        updateDTO.setName("Updated User");

        mockMvc.perform(put("/users/{userId}", "non-existent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        mockMvc.perform(delete("/users/{userId}", createdUser.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}", createdUser.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
        mockMvc.perform(delete("/users/{userId}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePasswordSuccessfully() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO(
                "NewPassword123",
                "NewPassword123",
                "Password123"
        );

        mockMvc.perform(put("/users/{userId}/password-update", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestWhenNewPasswordIsTooShort() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO(
                "New1",
                "New1",
                "Password123"
        );

        mockMvc.perform(put("/users/{userId}/password-update", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNewPasswordConfirmationDoesNotMatch() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO(
                "NewPassword123",
                "DifferentPassword123",
                "Password123"
        );

        mockMvc.perform(put("/users/{userId}/password-update", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCurrentPasswordIsIncorrect() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO(
                "NewPassword123",
                "NewPassword123",
                "WrongPassword123"
        );

        mockMvc.perform(put("/users/{userId}/password-update", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateUserAddressSuccessfully() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserOutputDTO createdUser = objectMapper.readValue(responseContent, UserOutputDTO.class);

        AddressInputDTO newAddressDTO = createValidAddressInputDTO();
        newAddressDTO.setStreet("New Address Street");
        newAddressDTO.setNumber(456);
        newAddressDTO.setCity("New City");

        mockMvc.perform(put("/users/{userId}/address", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAddressDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUser.getId()))
                .andExpect(jsonPath("$.address.street").value("New Address Street"))
                .andExpect(jsonPath("$.address.number").value(456))
                .andExpect(jsonPath("$.address.city").value("New City"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenLoginAlreadyExists() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated());

        UserCreateDTO duplicateDTO = createValidUserCreateDTO();
        duplicateDTO.setMail("different@email.com");
        duplicateDTO.setLogin("johndoe");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMailAlreadyExists() throws Exception {
        UserCreateDTO inputDTO = createValidUserCreateDTO();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated());

        UserCreateDTO duplicateDTO = createValidUserCreateDTO();
        duplicateDTO.setLogin("differentlogin");
        duplicateDTO.setMail("john.doe@example.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateDTO)))
                .andExpect(status().isBadRequest());
    }

    private UserCreateDTO createValidUserCreateDTO() {
        UserCreateDTO inputDTO = new UserCreateDTO();
        inputDTO.setName("John Doe");
        inputDTO.setMail("john.doe@example.com");
        inputDTO.setLogin("johndoe");
        inputDTO.setPassword("Password123");
        inputDTO.setPasswordConfirmation("Password123");
        inputDTO.setUserType(UserTypeEnum.CLIENT);
        inputDTO.setAddress(createValidAddressInputDTO());
        return inputDTO;
    }

    private UserUpdateDTO createValidUserUpdateDTO() {
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setName("John Doe Updated");
        updateDTO.setMail("john.updated@example.com");
        updateDTO.setLogin("johnupdated");
        updateDTO.setUserType(UserTypeEnum.CLIENT);
        return updateDTO;
    }

    private AddressInputDTO createValidAddressInputDTO() {
        AddressInputDTO dto = new AddressInputDTO();
        dto.setStreet("Main Street");
        dto.setNumber(123);
        dto.setComplement("Apt 1");
        dto.setNeighborhood("Downtown");
        dto.setCity("Test City");
        dto.setState("TS");
        dto.setCountry("Brazil");
        dto.setPostalCode("12345678");
        dto.setReference("Near test location");
        return dto;
    }
}