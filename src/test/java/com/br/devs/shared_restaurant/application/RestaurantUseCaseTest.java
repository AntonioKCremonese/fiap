package com.br.devs.shared_restaurant.application;

import com.br.devs.shared_restaurant.application.usecases.RestaurantUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.*;
import com.br.devs.shared_restaurant.core.entities.Address;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.gateways.ICuisineTypeGateway;
import com.br.devs.shared_restaurant.core.gateways.IRestaurantGateway;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
import com.br.devs.shared_restaurant.core.presenters.AddressPresenter;
import com.br.devs.shared_restaurant.core.presenters.RestaurantPresenter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantUseCaseTest {

    @InjectMocks
    private RestaurantUseCaseImpl restaurantUseCase;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ICuisineTypeGateway cuisineTypeGateway;

    @Mock
    private IUserGateway userGateway;

    @Test
    public void testCreateRestaurant() {
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("sbo");
        input.setAddress(address);
        var cuisineId = new CuisineTypeIdInputDTO();
        cuisineId.setId("123456");
        input.setCuisineType(cuisineId);

        var ownerId = new UserIdInputDTO();
        ownerId.setId("123456");
        input.setOwner(ownerId);

        var cuisineType = CuisineType.builder().id("123456").name("Mexican").build();
        Mockito.when(cuisineTypeGateway.findCuisineTypeById(Mockito.anyString())).thenReturn(cuisineType);
        var user = User.builder().id("123456").build();
        Mockito.when(userGateway.findUserById(Mockito.any())).thenReturn(user);

        var restaurant = RestaurantPresenter.toEntity(input);
        restaurant.setOwner(user);
        restaurant.setCuisineType(cuisineType);

        Mockito.when(restaurantGateway.save(Mockito.any())).thenReturn(restaurant);

        var createdRestaurant = restaurantUseCase.create(input);
        Assert.assertNotNull(createdRestaurant);
    }

    @Test
    public void testGetRestaurantById() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("sbo");
        input.setAddress(address);
        var restaurant = RestaurantPresenter.toEntity(input);
        var cuisineType = CuisineType.builder().id("123456").name("Mexican").build();
        var user = User.builder().id("123456").build();
        restaurant.setId(restaurantId);
        restaurant.setCuisineType(cuisineType);
        restaurant.setOwner(user);

        Mockito.when(restaurantGateway.findRestaurantById(Mockito.anyString())).thenReturn(restaurant);

        var foundRestaurant = restaurantUseCase.getById(restaurantId);

        Assert.assertNotNull(foundRestaurant);
        Assert.assertEquals(restaurantId, foundRestaurant.getId());
    }

    @Test
    public void testUpdateRestaurant() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Updated Restaurant");
        address.setNumber(1);
        address.setCity("sbo");
        input.setAddress(address);
        var cuisineId = new CuisineTypeIdInputDTO();
        cuisineId.setId("123456");
        input.setCuisineType(cuisineId);

        var ownerId = new UserIdInputDTO();
        ownerId.setId("123456");
        input.setOwner(ownerId);

        var existingRestaurant = RestaurantPresenter.toEntity(input);
        existingRestaurant.setId(restaurantId);

        var cuisineType = CuisineType.builder().id("123456").name("Mexican").build();
        Mockito.when(cuisineTypeGateway.findCuisineTypeById(Mockito.anyString())).thenReturn(cuisineType);

        var user = User.builder().id("123456").build();
        Mockito.when(userGateway.findUserById(Mockito.any())).thenReturn(user);
        Mockito.when(restaurantGateway.findRestaurantById(Mockito.anyString())).thenReturn(existingRestaurant);
        Mockito.when(restaurantGateway.save(Mockito.any())).thenReturn(existingRestaurant);

        var updatedRestaurant = restaurantUseCase.update(restaurantId, input);

        Assert.assertNotNull(updatedRestaurant);
        Assert.assertEquals("Updated Restaurant", updatedRestaurant.getName());
    }

    @Test
    public void testDeleteRestaurant() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("sbo");
        input.setAddress(address);
        var restaurant = RestaurantPresenter.toEntity(input);
        restaurant.setId(restaurantId);

        Mockito.when(restaurantGateway.findRestaurantById(Mockito.anyString())).thenReturn(restaurant);
        Mockito.doNothing().when(restaurantGateway).deleteRestaurantById(Mockito.anyString());

        restaurantUseCase.delete(restaurantId);

        Mockito.verify(restaurantGateway, Mockito.times(1)).deleteRestaurantById(restaurantId);
    }

    @Test
    public void testDeleteRestaurantThrowsException() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("sbo");
        input.setAddress(address);
        var restaurant = RestaurantPresenter.toEntity(input);
        restaurant.setId(restaurantId);

        Mockito.when(restaurantGateway.findRestaurantById(Mockito.anyString())).thenReturn(restaurant);
        Mockito.doThrow(new DataIntegrityViolationException("")).when(restaurantGateway).deleteRestaurantById(Mockito.anyString());

        try {
            restaurantUseCase.delete(restaurantId);
            Assert.fail("Expected exception not thrown");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof RuntimeException);
        }
    }
}
