package com.br.devs.shared_restaurant.application.usecases.menuitem;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.dto.output.MenuItemOutputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import com.br.devs.shared_restaurant.core.exceptions.MenuItemValidationException;
import com.br.devs.shared_restaurant.core.interfaces.ICuisineTypeUseCase;
import com.br.devs.shared_restaurant.core.interfaces.IMenuItemUseCase;
import com.br.devs.shared_restaurant.core.interfaces.IRestaurantUseCase;
import com.br.devs.shared_restaurant.core.interfaces.IUserUseCase;
import com.br.devs.shared_restaurant.utils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuItemUseCaseIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private IMenuItemUseCase menuItemUseCase;

    @Autowired
    private IRestaurantUseCase restaurantUseCase;

    @Autowired
    private ICuisineTypeUseCase cuisineTypeUseCase;

    @Autowired
    private IUserUseCase userUseCase;

    @Test
    void shouldCreateMenuItemSuccessfully() {
        RestaurantOutputDTO restaurant = createTestRestaurant("Pizzeria Bella", "Italian", "owner@pizzeria.com");

        MenuItemInputDTO inputDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Pizza Margherita")
                .withDescription("Classic Italian pizza with tomatoes, mozzarella, and basil")
                .withPrice(new BigDecimal("24.99"))
                .withAvailableForDineInOnly(false)
                .withPhotoPath("/images/pizza-margherita.jpg")
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemOutputDTO result = menuItemUseCase.create(inputDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("Pizza Margherita");
        assertThat(result.getDescription()).isEqualTo("Classic Italian pizza with tomatoes, mozzarella, and basil");
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("24.99"));
        assertThat(result.isAvailableForDineInOnly()).isFalse();
        assertThat(result.getPhotoPath()).isEqualTo("/images/pizza-margherita.jpg");
        assertThat(result.getRestaurant()).isNotNull();
        assertThat(result.getRestaurant().getId()).isEqualTo(restaurant.getId());
        assertThat(result.getRestaurant().getName()).isEqualTo("Pizzeria Bella");
    }

    @Test
    void shouldFindMenuItemById() {
        RestaurantOutputDTO restaurant = createTestRestaurant("Sushi Bar", "Japanese", "owner@sushi.com");

        MenuItemInputDTO inputDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Sashimi Set")
                .withDescription("Fresh assorted sashimi")
                .withPrice(new BigDecimal("45.00"))
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemOutputDTO createdMenuItem = menuItemUseCase.create(inputDTO);

        MenuItemOutputDTO foundMenuItem = menuItemUseCase.getById(createdMenuItem.getId());

        assertThat(foundMenuItem).isNotNull();
        assertThat(foundMenuItem.getId()).isEqualTo(createdMenuItem.getId());
        assertThat(foundMenuItem.getName()).isEqualTo("Sashimi Set");
        assertThat(foundMenuItem.getDescription()).isEqualTo("Fresh assorted sashimi");
        assertThat(foundMenuItem.getPrice()).isEqualTo(new BigDecimal("45.00"));
        assertThat(foundMenuItem.getRestaurant().getName()).isEqualTo("Sushi Bar");
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        RestaurantOutputDTO restaurant = createTestRestaurant("Burger House", "American", "owner@burger.com");

        MenuItemInputDTO inputDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Classic Burger")
                .withDescription("Beef burger with lettuce and tomato")
                .withPrice(new BigDecimal("18.50"))
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemOutputDTO createdMenuItem = menuItemUseCase.create(inputDTO);

        MenuItemInputDTO updateDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Deluxe Burger")
                .withDescription("Premium beef burger with bacon, cheese, lettuce and tomato")
                .withPrice(new BigDecimal("25.00"))
                .withAvailableForDineInOnly(true)
                .withPhotoPath("/images/deluxe-burger.jpg")
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemOutputDTO updatedMenuItem = menuItemUseCase.update(createdMenuItem.getId(), updateDTO);

        assertThat(updatedMenuItem).isNotNull();
        assertThat(updatedMenuItem.getId()).isEqualTo(createdMenuItem.getId());
        assertThat(updatedMenuItem.getName()).isEqualTo("Deluxe Burger");
        assertThat(updatedMenuItem.getDescription()).isEqualTo("Premium beef burger with bacon, cheese, lettuce and tomato");
        assertThat(updatedMenuItem.getPrice()).isEqualTo(new BigDecimal("25.00"));
        assertThat(updatedMenuItem.isAvailableForDineInOnly()).isTrue();
        assertThat(updatedMenuItem.getPhotoPath()).isEqualTo("/images/deluxe-burger.jpg");
        assertThat(updatedMenuItem.getRestaurant().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void shouldDeleteMenuItemSuccessfully() {
        RestaurantOutputDTO restaurant = createTestRestaurant("Taco Bell", "Mexican", "owner@taco.com");

        MenuItemInputDTO inputDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Chicken Tacos")
                .withDescription("Three soft tacos with grilled chicken")
                .withPrice(new BigDecimal("12.99"))
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemOutputDTO createdMenuItem = menuItemUseCase.create(inputDTO);

        menuItemUseCase.delete(createdMenuItem.getId());

        assertThatThrownBy(() -> menuItemUseCase.getById(createdMenuItem.getId()))
                .isInstanceOf(MenuItemValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemNotFound() {
        String nonExistentId = "non-existent-id";

        assertThatThrownBy(() -> menuItemUseCase.getById(nonExistentId))
                .isInstanceOf(MenuItemValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentMenuItem() {
        RestaurantOutputDTO restaurant = createTestRestaurant("Thai Garden", "Thai", "owner@thai.com");
        String nonExistentId = "non-existent-id";

        MenuItemInputDTO updateDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Pad Thai")
                .withRestaurantId(restaurant.getId())
                .build();

        assertThatThrownBy(() -> menuItemUseCase.update(nonExistentId, updateDTO))
                .isInstanceOf(MenuItemValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentMenuItem() {
        String nonExistentId = "non-existent-id";

        assertThatThrownBy(() -> menuItemUseCase.delete(nonExistentId))
                .isInstanceOf(MenuItemValidationException.class);
    }

    @Test
    void shouldCreateMultipleMenuItemsForSameRestaurant() {
        RestaurantOutputDTO restaurant = createTestRestaurant("Pasta Palace", "Italian", "owner@pasta.com");

        MenuItemInputDTO inputDTO1 = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Spaghetti Carbonara")
                .withDescription("Creamy pasta with bacon and eggs")
                .withPrice(new BigDecimal("22.50"))
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemInputDTO inputDTO2 = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Fettuccine Alfredo")
                .withDescription("Rich creamy pasta with parmesan")
                .withPrice(new BigDecimal("20.00"))
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemInputDTO inputDTO3 = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Lasagna")
                .withDescription("Traditional layered pasta with meat sauce")
                .withPrice(new BigDecimal("26.99"))
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemOutputDTO menuItem1 = menuItemUseCase.create(inputDTO1);
        MenuItemOutputDTO menuItem2 = menuItemUseCase.create(inputDTO2);
        MenuItemOutputDTO menuItem3 = menuItemUseCase.create(inputDTO3);

        assertThat(menuItem1.getName()).isEqualTo("Spaghetti Carbonara");
        assertThat(menuItem2.getName()).isEqualTo("Fettuccine Alfredo");
        assertThat(menuItem3.getName()).isEqualTo("Lasagna");

        assertThat(menuItem1.getId()).isNotEqualTo(menuItem2.getId());
        assertThat(menuItem2.getId()).isNotEqualTo(menuItem3.getId());
        assertThat(menuItem1.getId()).isNotEqualTo(menuItem3.getId());

        assertThat(menuItem1.getRestaurant().getId()).isEqualTo(restaurant.getId());
        assertThat(menuItem2.getRestaurant().getId()).isEqualTo(restaurant.getId());
        assertThat(menuItem3.getRestaurant().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void shouldCreateMenuItemsWithDifferentAvailabilitySettings() {
        RestaurantOutputDTO restaurant = createTestRestaurant("Fine Dining", "French", "owner@fine.com");

        MenuItemInputDTO dineInOnlyDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Beef Wellington")
                .withDescription("Premium beef in pastry - dine-in only")
                .withPrice(new BigDecimal("65.00"))
                .withAvailableForDineInOnly(true)
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemInputDTO takeawayDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("French Onion Soup")
                .withDescription("Traditional soup - available for takeaway")
                .withPrice(new BigDecimal("12.50"))
                .withAvailableForDineInOnly(false)
                .withRestaurantId(restaurant.getId())
                .build();

        MenuItemOutputDTO dineInItem = menuItemUseCase.create(dineInOnlyDTO);
        MenuItemOutputDTO takeawayItem = menuItemUseCase.create(takeawayDTO);

        assertThat(dineInItem.isAvailableForDineInOnly()).isTrue();
        assertThat(takeawayItem.isAvailableForDineInOnly()).isFalse();
        assertThat(dineInItem.getName()).isEqualTo("Beef Wellington");
        assertThat(takeawayItem.getName()).isEqualTo("French Onion Soup");
    }

    @Test
    void shouldUpdateMenuItemRestaurant() {
        RestaurantOutputDTO restaurant1 = createTestRestaurant("Old Restaurant", "Italian Cuisine", "owner1@test.com");
        RestaurantOutputDTO restaurant2 = createTestRestaurant("New Restaurant", "French Cuisine", "owner2@test.com");

        MenuItemInputDTO inputDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Signature Dish")
                .withDescription("Our special recipe")
                .withPrice(new BigDecimal("30.00"))
                .withRestaurantId(restaurant1.getId())
                .build();

        MenuItemOutputDTO createdMenuItem = menuItemUseCase.create(inputDTO);

        MenuItemInputDTO updateDTO = TestDataBuilder.defaultMenuItemInputDTO()
                .withName("Signature Dish")
                .withDescription("Our special recipe")
                .withPrice(new BigDecimal("30.00"))
                .withRestaurantId(restaurant2.getId())
                .build();

        MenuItemOutputDTO updatedMenuItem = menuItemUseCase.update(createdMenuItem.getId(), updateDTO);

        assertThat(updatedMenuItem.getRestaurant().getId()).isEqualTo(restaurant2.getId());
        assertThat(updatedMenuItem.getRestaurant().getName()).isEqualTo("New Restaurant");
    }

    private RestaurantOutputDTO createTestRestaurant(String name, String cuisineTypeName, String ownerEmail) {
        CuisineTypeInputDTO cuisineTypeInputDTO = new CuisineTypeInputDTO();
        cuisineTypeInputDTO.setName(cuisineTypeName);
        CuisineTypeOutputDTO cuisineType = cuisineTypeUseCase.create(cuisineTypeInputDTO);

        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withMail(ownerEmail)
                .withUserType(UserTypeEnum.OWNER)
                .buildCreateDTO();
        UserOutputDTO owner = userUseCase.createUser(userCreateDTO);

        RestaurantInputDTO restaurantInputDTO = createRestaurantInputDTO(name, "08:00-22:00", cuisineType.getId(), owner.getId());
        return restaurantUseCase.create(restaurantInputDTO);
    }

    private RestaurantInputDTO createRestaurantInputDTO(String name, String openingHours, String cuisineTypeId, String ownerId) {
        RestaurantInputDTO inputDTO = new RestaurantInputDTO();
        inputDTO.setName(name);
        inputDTO.setOpeningHours(openingHours);

        AddressInputDTO addressDTO = TestDataBuilder.defaultAddressInputDTO().build();
        inputDTO.setAddress(addressDTO);

        CuisineTypeIdInputDTO cuisineTypeIdDTO = new CuisineTypeIdInputDTO();
        cuisineTypeIdDTO.setId(cuisineTypeId);
        inputDTO.setCuisineType(cuisineTypeIdDTO);

        UserIdInputDTO ownerIdDTO = new UserIdInputDTO();
        ownerIdDTO.setId(ownerId);
        inputDTO.setOwner(ownerIdDTO);

        return inputDTO;
    }
}
