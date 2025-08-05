package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
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
class RestaurantControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRestaurantSuccessfully() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Restaurant"))
                .andExpect(jsonPath("$.openingHours").value("08:00-22:00"))
                .andExpect(jsonPath("$.cuisineType").exists())
                .andExpect(jsonPath("$.address").exists())
                .andExpect(jsonPath("$.owner").exists());
    }

    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        inputDTO.setName("");

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        inputDTO.setName(null);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameExceedsMaxLength() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        inputDTO.setName("A".repeat(101));

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOpeningHoursIsBlank() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        inputDTO.setOpeningHours("");

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOpeningHoursExceedsMaxLength() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        inputDTO.setOpeningHours("A".repeat(501));

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCuisineTypeIsNull() throws Exception {
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(null, ownerId);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenAddressIsNull() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        inputDTO.setAddress(null);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOwnerIsNull() throws Exception {
        String cuisineTypeId = createCuisineType();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, null);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetRestaurantByIdSuccessfully() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);

        String responseContent = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestaurantOutputDTO createdRestaurant = objectMapper.readValue(responseContent, RestaurantOutputDTO.class);

        mockMvc.perform(get("/restaurants/{id}", createdRestaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdRestaurant.getId()))
                .andExpect(jsonPath("$.name").value("Test Restaurant"))
                .andExpect(jsonPath("$.openingHours").value("08:00-22:00"));
    }

    @Test
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        mockMvc.perform(get("/restaurants/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);

        String responseContent = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestaurantOutputDTO createdRestaurant = objectMapper.readValue(responseContent, RestaurantOutputDTO.class);

        RestaurantInputDTO updateDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        updateDTO.setName("Updated Restaurant");
        updateDTO.setOpeningHours("09:00-23:00");

        mockMvc.perform(put("/restaurants/{id}", createdRestaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdRestaurant.getId()))
                .andExpect(jsonPath("$.name").value("Updated Restaurant"))
                .andExpect(jsonPath("$.openingHours").value("09:00-23:00"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateNameIsBlank() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);

        String responseContent = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestaurantOutputDTO createdRestaurant = objectMapper.readValue(responseContent, RestaurantOutputDTO.class);

        RestaurantInputDTO updateDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        updateDTO.setName("");

        mockMvc.perform(put("/restaurants/{id}", createdRestaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentRestaurant() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO updateDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);
        updateDTO.setName("Updated Restaurant");

        mockMvc.perform(put("/restaurants/{id}", "non-existent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO inputDTO = createValidRestaurantInputDTO(cuisineTypeId, ownerId);

        String responseContent = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestaurantOutputDTO createdRestaurant = objectMapper.readValue(responseContent, RestaurantOutputDTO.class);

        mockMvc.perform(delete("/restaurants/{id}", createdRestaurant.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/restaurants/{id}", createdRestaurant.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentRestaurant() throws Exception {
        mockMvc.perform(delete("/restaurants/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isBadRequest());
    }

    private RestaurantInputDTO createValidRestaurantInputDTO(String cuisineTypeId, String ownerId) {
        RestaurantInputDTO inputDTO = new RestaurantInputDTO();
        inputDTO.setName("Test Restaurant");
        inputDTO.setOpeningHours("08:00-22:00");

        if (cuisineTypeId != null) {
            CuisineTypeIdInputDTO cuisineTypeIdDTO = new CuisineTypeIdInputDTO();
            cuisineTypeIdDTO.setId(cuisineTypeId);
            inputDTO.setCuisineType(cuisineTypeIdDTO);
        }

        if (ownerId != null) {
            UserIdInputDTO ownerIdDTO = new UserIdInputDTO();
            ownerIdDTO.setId(ownerId);
            inputDTO.setOwner(ownerIdDTO);
        }

        AddressInputDTO addressDTO = createValidAddressInputDTO();
        inputDTO.setAddress(addressDTO);

        return inputDTO;
    }

    private AddressInputDTO createValidAddressInputDTO() {
        AddressInputDTO dto = new AddressInputDTO();
        dto.setStreet("Test Street");
        dto.setNumber(123);
        dto.setComplement("Apt 1");
        dto.setNeighborhood("Test Neighborhood");
        dto.setCity("Test City");
        dto.setState("TS");
        dto.setCountry("Brazil");
        dto.setPostalCode("12345678");
        dto.setReference("Near test location");
        return dto;
    }

    private String createCuisineType() throws Exception {
        CuisineTypeInputDTO cuisineTypeInput = new CuisineTypeInputDTO();
        cuisineTypeInput.setName("Italian");

        String responseContent = mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuisineTypeInput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(responseContent).get("id").asText();
    }

    private String createUser() throws Exception {
        UserCreateDTO userInput = new UserCreateDTO();
        userInput.setName("Test Owner");
        userInput.setMail("test@owner.com");
        userInput.setLogin("testowner");
        userInput.setPassword("Password123");
        userInput.setPasswordConfirmation("Password123");
        userInput.setUserType(com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum.OWNER);
        userInput.setAddress(createValidAddressInputDTO());

        String responseContent = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(responseContent).get("id").asText();
    }
}