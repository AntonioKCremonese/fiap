package com.br.devs.shared_restaurant.application.usecases.cuisine;

import com.br.devs.shared_restaurant.application.usecases.CuisineTypeUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.exceptions.CuisineTypeValidationException;
import com.br.devs.shared_restaurant.core.gateways.ICuisineTypeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuisineTypeUseCaseTest {

    @InjectMocks
    private CuisineTypeUseCaseImpl cuisineTypeUseCase;

    @Mock
    private ICuisineTypeGateway cuisineTypeGateway;

    @Test
    void shouldGetByIdSuccessfully() {
        when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(new CuisineType("12345", "Italian"));

        var cuisineFound = cuisineTypeUseCase.getById("12345");

        assertThat(cuisineFound).isNotNull();
        assertThat(cuisineFound.getId()).isEqualTo("12345");
    }

    @Test
    void shouldCreateCuisineTypeSuccessfully() {
        var input = new CuisineTypeInputDTO();
        input.setName("Italian");

        when(cuisineTypeGateway.save(any(CuisineType.class)))
                .thenReturn(new CuisineType("12345", "Italian"));

        var cuisineCreated = cuisineTypeUseCase.create(input);

        assertThat(cuisineCreated).isNotNull();
        assertThat(cuisineCreated.getId()).isEqualTo("12345");
    }

    @Test
    void shouldUpdateCuisineTypeSuccessfully() {
        var input = new CuisineTypeInputDTO();
        input.setName("Italian Barbecue");

        var cuisineType = new CuisineType("12345", "Italian");
        when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(cuisineType);
        when(cuisineTypeGateway.save(any(CuisineType.class)))
                .thenReturn(cuisineType);

        var cuisineUpdated = cuisineTypeUseCase.update("12345", input);

        assertThat(cuisineUpdated).isNotNull();
        assertThat(cuisineUpdated.getName()).isEqualTo("Italian Barbecue");
    }

    @Test
    void shouldDeleteCuisineTypeSuccessfully() {
        var cuisineType = new CuisineType("12345", "Italian");
        when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(cuisineType);

        cuisineTypeUseCase.delete("12345");

        verify(cuisineTypeGateway).deleteCuisineTypeById("12345");
    }

    @Test
    void shouldThrowExceptionWhenDeletingCuisineTypeWithDataIntegrityViolation() {
        var cuisineType = new CuisineType("12345", "Italian");
        when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(cuisineType);
        doThrow(new DataIntegrityViolationException(""))
                .when(cuisineTypeGateway).deleteCuisineTypeById("12345");

        assertThatThrownBy(() -> cuisineTypeUseCase.delete("12345"))
                .isInstanceOf(CuisineTypeValidationException.class);
    }
}