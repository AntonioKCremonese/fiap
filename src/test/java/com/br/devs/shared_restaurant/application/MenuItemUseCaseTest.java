package com.br.devs.shared_restaurant.application;

import com.br.devs.shared_restaurant.application.usecases.MenuItemUseCaseImpl;
import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantIdInputDTO;
import com.br.devs.shared_restaurant.core.entities.*;
import com.br.devs.shared_restaurant.core.exceptions.MenuItemValidationException;
import com.br.devs.shared_restaurant.core.gateways.IMenuItemGateway;
import com.br.devs.shared_restaurant.core.gateways.IRestaurantGateway;
import com.br.devs.shared_restaurant.core.presenters.MenuItemPresenter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class MenuItemUseCaseTest {

    @InjectMocks
    private MenuItemUseCaseImpl menuItemUseCase;

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Test
    public void testCreateMenuItem() {
        var input = new MenuItemInputDTO();
        input.setName("Pizza Margherita");
        input.setDescription("Classic Italian pizza with tomatoes, mozzarella, and basil");
        input.setPrice(new BigDecimal("12.99"));

        var restaurantIdInputDTO = new RestaurantIdInputDTO();
        restaurantIdInputDTO.setId("12345");
        input.setRestaurant(restaurantIdInputDTO);

        var address = Address.builder().id("123456").city("sbo").build();
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

        Mockito.when(restaurantGateway.findRestaurantById(anyString())).thenReturn(restaurant);
        Mockito.when(menuItemGateway.save(Mockito.any())).thenReturn(menuItem);

        var menuCreated = menuItemUseCase.create(input);

        Assert.assertNotNull(menuCreated);
    }

    @Test(expected = MenuItemValidationException.class)
    public void testCreateMenuItemWithNameAlreadyExistsOnRestaurant() {
        var input = new MenuItemInputDTO();
        input.setName("Pizza Margherita");
        input.setDescription("Classic Italian pizza with tomatoes, mozzarella, and basil");
        input.setPrice(new BigDecimal("12.99"));

        var restaurantIdInputDTO = new RestaurantIdInputDTO();
        restaurantIdInputDTO.setId("12345");
        input.setRestaurant(restaurantIdInputDTO);

        var address = Address.builder().id("123456").city("sbo").build();
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

        Mockito.when(restaurantGateway.findRestaurantById(anyString())).thenReturn(restaurant);
        Mockito.doThrow(MenuItemValidationException.menuItemAlreadyExistsForRestaurant())
                .when(menuItemGateway).validateMenuItemAlreadyExistsOnRestaurant(anyString(), anyString());

        menuItemUseCase.create(input);
    }

    @Test
    public void testGetMenuItemById() {
        var address = Address.builder().id("123456").city("sbo").build();
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

        Mockito.when(menuItemGateway.findMenuItemById(anyString())).thenReturn(menuItem);

        var menuFound = menuItemUseCase.getById("12345");

        Assert.assertNotNull(menuFound);
        Assert.assertEquals("12345", menuFound.getId());
    }

    @Test
    public void testUpdateMenuItem() {
        var input = new MenuItemInputDTO();
        input.setName("Pizza Margherita");
        input.setDescription("Classic Italian pizza with tomatoes, mozzarella, and basil");
        input.setPrice(new BigDecimal("12.99"));

        var restaurantIdInputDTO = new RestaurantIdInputDTO();
        restaurantIdInputDTO.setId("12345");
        input.setRestaurant(restaurantIdInputDTO);

        var address = Address.builder().id("123456").city("sbo").build();
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

        Mockito.when(menuItemGateway.findMenuItemById(anyString())).thenReturn(menuItem);
        Mockito.when(restaurantGateway.findRestaurantById(anyString())).thenReturn(restaurant);
        Mockito.when(menuItemGateway.save(Mockito.any())).thenReturn(menuItem);

        var menuUpdated = menuItemUseCase.update("12345", input);

        Assert.assertNotNull(menuUpdated);
        Assert.assertEquals("12345", menuUpdated.getId());
        Assert.assertEquals("Pizza Margherita", menuUpdated.getName());
    }

    @Test
    public void testDeleteMenuItem() {
        var address = Address.builder().id("123456").city("sbo").build();
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

        Mockito.when(menuItemGateway.findMenuItemById(anyString())).thenReturn(menuItem);
        Mockito.doNothing().when(menuItemGateway).deleteMenuItemById(anyString());

        menuItemUseCase.delete("12345");

        Mockito.verify(menuItemGateway, Mockito.times(1)).deleteMenuItemById("12345");
    }
}
