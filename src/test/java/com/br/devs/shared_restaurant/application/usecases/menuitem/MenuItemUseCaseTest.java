package com.br.devs.shared_restaurant.application.usecases.menuitem;

import com.br.devs.shared_restaurant.application.usecases.MenuItemUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantIdInputDTO;
import com.br.devs.shared_restaurant.core.entities.Address;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.entities.MenuItem;
import com.br.devs.shared_restaurant.core.entities.Restaurant;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.exceptions.MenuItemValidationException;
import com.br.devs.shared_restaurant.core.gateways.IMenuItemGateway;
import com.br.devs.shared_restaurant.core.gateways.IRestaurantGateway;
import com.br.devs.shared_restaurant.core.presenters.MenuItemPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemUseCaseTest {

    @InjectMocks
    private MenuItemUseCaseImpl menuItemUseCase;

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Test
    void shouldCreateMenuItemSuccessfully() {
        var input = new MenuItemInputDTO();
        input.setName("Pizza Margherita");
        input.setDescription("Classic Italian pizza with tomatoes, mozzarella, and basil");
        input.setPrice(new BigDecimal("12.99"));

        var restaurantIdInputDTO = new RestaurantIdInputDTO();
        restaurantIdInputDTO.setId("12345");
        input.setRestaurant(restaurantIdInputDTO);

        var address = Address.builder().id("123456").city("SBO").build();
        var cuisineType = CuisineType.builder().id("123456").name("Italian").build();
        var owner = User.builder().id("12345").name("Mario Rossi").mail("mario@rossi.com").build();

        var restaurant = Restaurant.builder()
                .id("12345")
                .name("Pizzeria Bella Napoli")
                .address(address)
                .cuisineType(cuisineType)
                .owner(owner)
                .build();

        var menuItem = MenuItemPresenter.toEntity(input);
        menuItem.setRestaurant(restaurant);

        when(restaurantGateway.findRestaurantById(anyString())).thenReturn(restaurant);
        when(menuItemGateway.save(any())).thenReturn(menuItem);

        var menuCreated = menuItemUseCase.create(input);

        assertThat(menuCreated).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenMenuItemNameAlreadyExistsOnRestaurant() {
        var input = new MenuItemInputDTO();
        input.setName("Pizza Margherita");
        input.setDescription("Classic Italian pizza with tomatoes, mozzarella, and basil");
        input.setPrice(new BigDecimal("12.99"));

        var restaurantIdInputDTO = new RestaurantIdInputDTO();
        restaurantIdInputDTO.setId("12345");
        input.setRestaurant(restaurantIdInputDTO);

        var address = Address.builder().id("123456").city("SBO").build();
        var cuisineType = CuisineType.builder().id("123456").name("Italian").build();
        var owner = User.builder().id("12345").name("Mario Rossi").mail("mario@rossi.com").build();

        var restaurant = Restaurant.builder()
                .id("12345")
                .name("Pizzeria Bella Napoli")
                .address(address)
                .cuisineType(cuisineType)
                .owner(owner)
                .build();

        when(restaurantGateway.findRestaurantById(anyString())).thenReturn(restaurant);
        doThrow(MenuItemValidationException.menuItemAlreadyExistsForRestaurant())
                .when(menuItemGateway).validateMenuItemAlreadyExistsOnRestaurant(anyString(), anyString());

        assertThatThrownBy(() -> menuItemUseCase.create(input))
                .isInstanceOf(MenuItemValidationException.class);
    }

    @Test
    void shouldGetMenuItemByIdSuccessfully() {
        var address = Address.builder().id("123456").city("SBO").build();
        var cuisineType = CuisineType.builder().id("123456").name("Italian").build();
        var owner = User.builder().id("12345").name("Mario Rossi").mail("mario@rossi.com").build();

        var restaurant = Restaurant.builder()
                .id("12345")
                .name("Pizzeria Bella Napoli")
                .address(address)
                .cuisineType(cuisineType)
                .owner(owner)
                .build();

        var menuItem = MenuItem.builder()
                .id("12345")
                .name("Pizza Margherita")
                .description("Classic Italian pizza with tomatoes, mozzarella, and basil")
                .price(new BigDecimal("12.99"))
                .restaurant(restaurant)
                .build();

        when(menuItemGateway.findMenuItemById(anyString())).thenReturn(menuItem);

        var menuFound = menuItemUseCase.getById("12345");

        assertThat(menuFound).isNotNull();
        assertThat(menuFound.getId()).isEqualTo("12345");
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        var input = new MenuItemInputDTO();
        input.setName("Pizza Margherita");
        input.setDescription("Classic Italian pizza with tomatoes, mozzarella, and basil");
        input.setPrice(new BigDecimal("12.99"));

        var restaurantIdInputDTO = new RestaurantIdInputDTO();
        restaurantIdInputDTO.setId("12345");
        input.setRestaurant(restaurantIdInputDTO);

        var address = Address.builder().id("123456").city("SBO").build();
        var cuisineType = CuisineType.builder().id("123456").name("Italian").build();
        var owner = User.builder().id("12345").name("Mario Rossi").mail("mario.rossi@com").build();

        var restaurant = Restaurant.builder()
                .id("12345")
                .name("Pizzeria Bella Napoli")
                .address(address)
                .cuisineType(cuisineType)
                .owner(owner)
                .build();

        var menuItem = MenuItemPresenter.toEntity(input);
        menuItem.setId("12345");
        menuItem.setRestaurant(restaurant);

        when(menuItemGateway.findMenuItemById(anyString())).thenReturn(menuItem);
        when(restaurantGateway.findRestaurantById(anyString())).thenReturn(restaurant);
        when(menuItemGateway.save(any())).thenReturn(menuItem);

        var menuUpdated = menuItemUseCase.update("12345", input);

        assertThat(menuUpdated).isNotNull();
        assertThat(menuUpdated.getId()).isEqualTo("12345");
        assertThat(menuUpdated.getName()).isEqualTo("Pizza Margherita");
    }

    @Test
    void shouldDeleteMenuItemSuccessfully() {
        var address = Address.builder().id("123456").city("SBO").build();
        var cuisineType = CuisineType.builder().id("123456").name("Italian").build();
        var owner = User.builder().id("12345").name("Mario Rossi").mail("mario.rossi@com").build();

        var restaurant = Restaurant.builder()
                .id("12345")
                .name("Pizzeria Bella Napoli")
                .address(address)
                .cuisineType(cuisineType)
                .owner(owner)
                .build();

        var menuItem = MenuItem.builder()
                .id("12345")
                .name("Pizza Margherita")
                .description("Classic Italian pizza with tomatoes, mozzarella, and basil")
                .price(new BigDecimal("12.99"))
                .restaurant(restaurant)
                .build();

        when(menuItemGateway.findMenuItemById(anyString())).thenReturn(menuItem);

        menuItemUseCase.delete("12345");

        verify(menuItemGateway).deleteMenuItemById("12345");
    }
}