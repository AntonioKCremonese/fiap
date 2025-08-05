package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.MenuItemOutputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class MenuItemControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateMenuItemSuccessfully() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Pizza Margherita"))
                .andExpect(jsonPath("$.description").value("Classic Italian pizza with tomatoes, mozzarella, and basil"))
                .andExpect(jsonPath("$.price").value(15.99))
                .andExpect(jsonPath("$.availableForDineInOnly").value(false))
                .andExpect(jsonPath("$.restaurant").exists());
    }

    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setName("");

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setName(null);

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameExceedsMaxLength() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setName("A".repeat(101));

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenDescriptionExceedsMaxLength() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setDescription("A".repeat(501));

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPriceIsNull() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setPrice(null);

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setPrice(new BigDecimal("-1.00"));

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPriceIsZero() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setPrice(BigDecimal.ZERO);

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenRestaurantIsNull() throws Exception {
        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(null);

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPhotoPathExceedsMaxLength() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);
        inputDTO.setPhotoPath("A".repeat(501));

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetMenuItemByIdSuccessfully() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);

        String responseContent = mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MenuItemOutputDTO createdMenuItem = objectMapper.readValue(responseContent, MenuItemOutputDTO.class);

        mockMvc.perform(get("/menu-items/{id}", createdMenuItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdMenuItem.getId()))
                .andExpect(jsonPath("$.name").value("Pizza Margherita"))
                .andExpect(jsonPath("$.description").value("Classic Italian pizza with tomatoes, mozzarella, and basil"))
                .andExpect(jsonPath("$.price").value(15.99));
    }

    @Test
    void shouldReturnNotFoundWhenMenuItemDoesNotExist() throws Exception {
        mockMvc.perform(get("/menu-items/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);

        String responseContent = mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MenuItemOutputDTO createdMenuItem = objectMapper.readValue(responseContent, MenuItemOutputDTO.class);

        MenuItemInputDTO updateDTO = createValidMenuItemInputDTO(restaurantId);
        updateDTO.setName("Pizza Quattro Stagioni");
        updateDTO.setDescription("Four seasons pizza with artichokes, mushrooms, ham and olives");
        updateDTO.setPrice(new BigDecimal("18.99"));
        updateDTO.setAvailableForDineInOnly(true);
        updateDTO.setPhotoPath("/images/quattro-stagioni.jpg");

        mockMvc.perform(put("/menu-items/{id}", createdMenuItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdMenuItem.getId()))
                .andExpect(jsonPath("$.name").value("Pizza Quattro Stagioni"))
                .andExpect(jsonPath("$.description").value("Four seasons pizza with artichokes, mushrooms, ham and olives"))
                .andExpect(jsonPath("$.price").value(18.99))
                .andExpect(jsonPath("$.availableForDineInOnly").value(true))
                .andExpect(jsonPath("$.photoPath").value("/images/quattro-stagioni.jpg"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateNameIsBlank() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);

        String responseContent = mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MenuItemOutputDTO createdMenuItem = objectMapper.readValue(responseContent, MenuItemOutputDTO.class);

        MenuItemInputDTO updateDTO = createValidMenuItemInputDTO(restaurantId);
        updateDTO.setName("");

        mockMvc.perform(put("/menu-items/{id}", createdMenuItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentMenuItem() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO updateDTO = createValidMenuItemInputDTO(restaurantId);
        updateDTO.setName("Updated Menu Item");

        mockMvc.perform(put("/menu-items/{id}", "non-existent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteMenuItemSuccessfully() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);

        String responseContent = mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MenuItemOutputDTO createdMenuItem = objectMapper.readValue(responseContent, MenuItemOutputDTO.class);

        mockMvc.perform(delete("/menu-items/{id}", createdMenuItem.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/menu-items/{id}", createdMenuItem.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentMenuItem() throws Exception {
        mockMvc.perform(delete("/menu-items/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnConflictWhenMenuItemNameAlreadyExistsOnRestaurant() throws Exception {
        String restaurantId = createRestaurant();

        MenuItemInputDTO inputDTO = createValidMenuItemInputDTO(restaurantId);

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated());

        MenuItemInputDTO duplicateDTO = createValidMenuItemInputDTO(restaurantId);
        duplicateDTO.setName("Pizza Margherita");

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateDTO)))
                .andExpect(status().isConflict());
    }

    private MenuItemInputDTO createValidMenuItemInputDTO(String restaurantId) {
        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Pizza Margherita");
        inputDTO.setDescription("Classic Italian pizza with tomatoes, mozzarella, and basil");
        inputDTO.setPrice(new BigDecimal("15.99"));
        inputDTO.setAvailableForDineInOnly(false);
        inputDTO.setPhotoPath("/images/pizza-margherita.jpg");

        if (restaurantId != null) {
            RestaurantIdInputDTO restaurantIdDTO = new RestaurantIdInputDTO();
            restaurantIdDTO.setId(restaurantId);
            inputDTO.setRestaurant(restaurantIdDTO);
        }

        return inputDTO;
    }

    private String createRestaurant() throws Exception {
        String cuisineTypeId = createCuisineType();
        String ownerId = createUser();

        RestaurantInputDTO restaurantInput = new RestaurantInputDTO();
        restaurantInput.setName("Test Restaurant");
        restaurantInput.setOpeningHours("08:00-22:00");

        CuisineTypeIdInputDTO cuisineTypeIdDTO = new CuisineTypeIdInputDTO();
        cuisineTypeIdDTO.setId(cuisineTypeId);
        restaurantInput.setCuisineType(cuisineTypeIdDTO);

        UserIdInputDTO ownerIdDTO = new UserIdInputDTO();
        ownerIdDTO.setId(ownerId);
        restaurantInput.setOwner(ownerIdDTO);

        restaurantInput.setAddress(createValidAddressInputDTO());

        String responseContent = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantInput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(responseContent).get("id").asText();
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