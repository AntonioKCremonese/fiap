package com.br.devs.shared_restaurant.application.usecases.cuisine;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.exceptions.CuisineTypeValidationException;
import com.br.devs.shared_restaurant.core.interfaces.ICuisineTypeUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CuisineTypeUseCaseIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ICuisineTypeUseCase cuisineTypeUseCase;

    @Test
    void shouldCreateCuisineTypeSuccessfully() {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName("Italian");

        CuisineTypeOutputDTO result = cuisineTypeUseCase.create(inputDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("Italian");
    }

    @Test
    void shouldFindCuisineTypeById() {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName("Japanese");

        CuisineTypeOutputDTO createdCuisine = cuisineTypeUseCase.create(inputDTO);

        CuisineTypeOutputDTO foundCuisine = cuisineTypeUseCase.getById(createdCuisine.getId());

        assertThat(foundCuisine).isNotNull();
        assertThat(foundCuisine.getId()).isEqualTo(createdCuisine.getId());
        assertThat(foundCuisine.getName()).isEqualTo("Japanese");
    }

    @Test
    void shouldUpdateCuisineTypeSuccessfully() {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName("Mexican");

        CuisineTypeOutputDTO createdCuisine = cuisineTypeUseCase.create(inputDTO);

        CuisineTypeInputDTO updateDTO = new CuisineTypeInputDTO();
        updateDTO.setName("Mexican Fusion");

        CuisineTypeOutputDTO updatedCuisine = cuisineTypeUseCase.update(createdCuisine.getId(), updateDTO);

        assertThat(updatedCuisine).isNotNull();
        assertThat(updatedCuisine.getId()).isEqualTo(createdCuisine.getId());
        assertThat(updatedCuisine.getName()).isEqualTo("Mexican Fusion");
    }

    @Test
    void shouldDeleteCuisineTypeSuccessfully() {
        CuisineTypeInputDTO inputDTO = new CuisineTypeInputDTO();
        inputDTO.setName("Thai");

        CuisineTypeOutputDTO createdCuisine = cuisineTypeUseCase.create(inputDTO);

        cuisineTypeUseCase.delete(createdCuisine.getId());

        assertThatThrownBy(() -> cuisineTypeUseCase.getById(createdCuisine.getId()))
                .isInstanceOf(CuisineTypeValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenCuisineTypeNotFound() {
        String nonExistentId = "non-existent-id";

        assertThatThrownBy(() -> cuisineTypeUseCase.getById(nonExistentId))
                .isInstanceOf(CuisineTypeValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCuisineType() {
        String nonExistentId = "non-existent-id";
        CuisineTypeInputDTO updateDTO = new CuisineTypeInputDTO();
        updateDTO.setName("Updated Name");

        assertThatThrownBy(() -> cuisineTypeUseCase.update(nonExistentId, updateDTO))
                .isInstanceOf(CuisineTypeValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCuisineType() {
        String nonExistentId = "non-existent-id";

        assertThatThrownBy(() -> cuisineTypeUseCase.delete(nonExistentId))
                .isInstanceOf(CuisineTypeValidationException.class);
    }

    @Test
    void shouldCreateMultipleCuisineTypesWithDifferentNames() {
        CuisineTypeInputDTO inputDTO1 = new CuisineTypeInputDTO();
        inputDTO1.setName("Indian");
        CuisineTypeOutputDTO cuisine1 = cuisineTypeUseCase.create(inputDTO1);

        CuisineTypeInputDTO inputDTO2 = new CuisineTypeInputDTO();
        inputDTO2.setName("Brazilian");
        CuisineTypeOutputDTO cuisine2 = cuisineTypeUseCase.create(inputDTO2);

        CuisineTypeInputDTO inputDTO3 = new CuisineTypeInputDTO();
        inputDTO3.setName("Mediterranean");
        CuisineTypeOutputDTO cuisine3 = cuisineTypeUseCase.create(inputDTO3);

        assertThat(cuisine1.getName()).isEqualTo("Indian");
        assertThat(cuisine2.getName()).isEqualTo("Brazilian");
        assertThat(cuisine3.getName()).isEqualTo("Mediterranean");

        assertThat(cuisine1.getId()).isNotEqualTo(cuisine2.getId());
        assertThat(cuisine2.getId()).isNotEqualTo(cuisine3.getId());
        assertThat(cuisine1.getId()).isNotEqualTo(cuisine3.getId());
    }
}