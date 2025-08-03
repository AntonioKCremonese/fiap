package com.br.devs.shared_restaurant.application;

import com.br.devs.shared_restaurant.application.usecases.CuisineTypeUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.exceptions.CuisineTypeValidationException;
import com.br.devs.shared_restaurant.core.gateways.ICuisineTypeGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

@RunWith(MockitoJUnitRunner.class)
public class CuisineTypeUseCaseTest {

    @InjectMocks
    private CuisineTypeUseCaseImpl cuisineTypeUseCase;

    @Mock
    private ICuisineTypeGateway cuisineTypeGateway;

    @Test
    public void testGetByIdSuccessFully() {
        Mockito.when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(new CuisineType("12345", "Italian"));
        var cuisineFounded = cuisineTypeUseCase.getById("12345");

        Assert.assertNotNull(cuisineFounded);
        Assert.assertEquals("12345", cuisineFounded.getId());
    }

    @Test
    public void testCreateCuisineTypeSuccessFully() {
        var input = new CuisineTypeInputDTO();
        input.setName("Italian");

        Mockito.when(cuisineTypeGateway.save(Mockito.any(CuisineType.class)))
                .thenReturn(new CuisineType("12345", "Italian"));

        var cuisineFounded = cuisineTypeUseCase.create(input);

        Assert.assertNotNull(cuisineFounded);
        Assert.assertEquals("12345", cuisineFounded.getId());
    }

    @Test
    public void testUpdateCuisineTypeSuccessFully() {
        var input = new CuisineTypeInputDTO();
        input.setName("Italian Barbecue");

        var cuisineType = new CuisineType("12345", "Italian");
        Mockito.when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(cuisineType);
        Mockito.when(cuisineTypeGateway.save(Mockito.any(CuisineType.class)))
                .thenReturn(cuisineType);

        var cuisineFounded = cuisineTypeUseCase.update("12345", input);

        Assert.assertNotNull(cuisineFounded);
        Assert.assertEquals("Italian Barbecue", cuisineFounded.getName());
    }

    @Test
    public void testDeleteCuisineTypeSuccessFully() {
        var cuisineType = new CuisineType("12345", "Italian");
        Mockito.when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(cuisineType);

        cuisineTypeUseCase.delete("12345");

        Mockito.verify(cuisineTypeGateway).deleteCuisineTypeById("12345");
    }

    @Test
    public void testDeleteCuisineTypeWithDataIntegrityViolation() {
        var cuisineType = new CuisineType("12345", "Italian");
        Mockito.when(cuisineTypeGateway.findCuisineTypeById("12345"))
                .thenReturn(cuisineType);
        Mockito.doThrow(new DataIntegrityViolationException("")).when(cuisineTypeGateway).deleteCuisineTypeById("12345");

        try {
            cuisineTypeUseCase.delete("12345");
            Assert.fail("Expected CuisineTypeValidationException to be thrown");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CuisineTypeValidationException);
        }
    }
}
