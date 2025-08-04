package com.br.devs.shared_restaurant.application.usecases.restaurant;

import com.br.devs.shared_restaurant.application.usecases.RestaurantUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserIdInputDTO;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.gateways.ICuisineTypeGateway;
import com.br.devs.shared_restaurant.core.gateways.IRestaurantGateway;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
import com.br.devs.shared_restaurant.core.presenters.RestaurantPresenter;
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
class RestaurantUseCaseTest {

    @InjectMocks
    private RestaurantUseCaseImpl restaurantUseCase;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ICuisineTypeGateway cuisineTypeGateway;

    @Mock
    private IUserGateway userGateway;

    @Test
    void shouldCreateRestaurantSuccessfully() {
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("SBO");
        input.setAddress(address);

        var cuisineId = new CuisineTypeIdInputDTO();
        cuisineId.setId("123456");
        input.setCuisineType(cuisineId);

        var ownerId = new UserIdInputDTO();
        ownerId.setId("123456");
        input.setOwner(ownerId);

        var cuisineType = CuisineType.builder().id("123456").name("Mexican").build();
        when(cuisineTypeGateway.findCuisineTypeById(any())).thenReturn(cuisineType);

        var user = User.builder().id("123456").build();
        when(userGateway.findUserById(any())).thenReturn(user);

        var restaurant = RestaurantPresenter.toEntity(input);
        restaurant.setOwner(user);
        restaurant.setCuisineType(cuisineType);

        when(restaurantGateway.save(any())).thenReturn(restaurant);

        var createdRestaurant = restaurantUseCase.create(input);

        assertThat(createdRestaurant).isNotNull();
    }

    @Test
    void shouldGetRestaurantByIdSuccessfully() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("SBO");
        input.setAddress(address);

        var restaurant = RestaurantPresenter.toEntity(input);
        var cuisineType = CuisineType.builder().id("123456").name("Mexican").build();
        var user = User.builder().id("123456").build();
        restaurant.setId(restaurantId);
        restaurant.setCuisineType(cuisineType);
        restaurant.setOwner(user);

        when(restaurantGateway.findRestaurantById(any())).thenReturn(restaurant);

        var foundRestaurant = restaurantUseCase.getById(restaurantId);

        assertThat(foundRestaurant).isNotNull();
        assertThat(foundRestaurant.getId()).isEqualTo(restaurantId);
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Updated Restaurant");
        address.setNumber(1);
        address.setCity("SBO");
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
        when(cuisineTypeGateway.findCuisineTypeById(any())).thenReturn(cuisineType);

        var user = User.builder().id("123456").build();
        when(userGateway.findUserById(any())).thenReturn(user);
        when(restaurantGateway.findRestaurantById(any())).thenReturn(existingRestaurant);
        when(restaurantGateway.save(any())).thenReturn(existingRestaurant);

        var updatedRestaurant = restaurantUseCase.update(restaurantId, input);

        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getName()).isEqualTo("Updated Restaurant");
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("SBO");
        input.setAddress(address);

        var restaurant = RestaurantPresenter.toEntity(input);
        restaurant.setId(restaurantId);

        when(restaurantGateway.findRestaurantById(any())).thenReturn(restaurant);

        restaurantUseCase.delete(restaurantId);

        verify(restaurantGateway).deleteRestaurantById(restaurantId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingRestaurantWithDataIntegrityViolation() {
        var restaurantId = "123456";
        var input = new RestaurantInputDTO();
        var address = new AddressInputDTO();

        input.setName("Test Restaurant");
        address.setNumber(1);
        address.setCity("SBO");
        input.setAddress(address);

        var restaurant = RestaurantPresenter.toEntity(input);
        restaurant.setId(restaurantId);

        when(restaurantGateway.findRestaurantById(any())).thenReturn(restaurant);
        doThrow(new DataIntegrityViolationException(""))
                .when(restaurantGateway).deleteRestaurantById(any());

        assertThatThrownBy(() -> restaurantUseCase.delete(restaurantId))
                .isInstanceOf(RuntimeException.class);
    }
}