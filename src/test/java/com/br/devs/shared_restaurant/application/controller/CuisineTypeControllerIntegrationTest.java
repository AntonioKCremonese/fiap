package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.utils.TestDataBuilder;
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
class CuisineTypeControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCuisineTypeSuccessfully() throws Exception {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName("Italian");

        mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Italian"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName("");

        mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName(null);

        mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameExceedsMaxLength() throws Exception {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName("A".repeat(101));

        mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetCuisineTypeByIdSuccessfully() throws Exception {
        CuisineType cuisineType = TestDataBuilder.defaultCuisineType().build();
        CuisineTypeInputDTO inputDTO = toCuisineTypeInputDTO(cuisineType);

        String responseContent = mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CuisineTypeOutputDTO createdCuisineType = objectMapper.readValue(responseContent, CuisineTypeOutputDTO.class);

        mockMvc.perform(get("/cuisine-types/{id}", createdCuisineType.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCuisineType.getId()))
                .andExpect(jsonPath("$.name").value(cuisineType.getName()));
    }

    @Test
    void shouldReturnNotFoundWhenCuisineTypeDoesNotExist() throws Exception {
        mockMvc.perform(get("/cuisine-types/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateCuisineTypeSuccessfully() throws Exception {
        CuisineType cuisineType = TestDataBuilder.defaultCuisineType().build();
        CuisineTypeInputDTO inputDTO = toCuisineTypeInputDTO(cuisineType);

        String responseContent = mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CuisineTypeOutputDTO createdCuisineType = objectMapper.readValue(responseContent, CuisineTypeOutputDTO.class);

        CuisineTypeInputDTO updateDTO = new CuisineTypeInputDTO();
        updateDTO.setName("Updated Cuisine");

        mockMvc.perform(put("/cuisine-types/{id}", createdCuisineType.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCuisineType.getId()))
                .andExpect(jsonPath("$.name").value("Updated Cuisine"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateNameIsBlank() throws Exception {
        CuisineType cuisineType = TestDataBuilder.defaultCuisineType().build();
        CuisineTypeInputDTO inputDTO = toCuisineTypeInputDTO(cuisineType);

        String responseContent = mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CuisineTypeOutputDTO createdCuisineType = objectMapper.readValue(responseContent, CuisineTypeOutputDTO.class);

        CuisineTypeInputDTO updateDTO = new CuisineTypeInputDTO();
        updateDTO.setName("");

        mockMvc.perform(put("/cuisine-types/{id}", createdCuisineType.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentCuisineType() throws Exception {
        CuisineTypeInputDTO updateDTO = new CuisineTypeInputDTO();
        updateDTO.setName("Updated Cuisine");

        mockMvc.perform(put("/cuisine-types/{id}", "non-existent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCuisineTypeSuccessfully() throws Exception {
        CuisineType cuisineType = TestDataBuilder.defaultCuisineType().build();
        CuisineTypeInputDTO inputDTO = toCuisineTypeInputDTO(cuisineType);

        String responseContent = mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CuisineTypeOutputDTO createdCuisineType = objectMapper.readValue(responseContent, CuisineTypeOutputDTO.class);

        mockMvc.perform(delete("/cuisine-types/{id}", createdCuisineType.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cuisine-types/{id}", createdCuisineType.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentCuisineType() throws Exception {
        mockMvc.perform(delete("/cuisine-types/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
        mockMvc.perform(post("/cuisine-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isBadRequest());
    }

    private CuisineTypeInputDTO toCuisineTypeInputDTO(com.br.devs.shared_restaurant.core.entities.CuisineType cuisineType) {
        CuisineTypeInputDTO dto = new CuisineTypeInputDTO();
        dto.setName(cuisineType.getName());
        return dto;
    }
}