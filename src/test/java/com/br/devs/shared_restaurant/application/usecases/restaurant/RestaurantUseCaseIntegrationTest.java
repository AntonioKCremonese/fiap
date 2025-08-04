package com.br.devs.shared_restaurant.application.usecases.restaurant;

import com.br.devs.shared_restaurant.config.BaseIntegrationTest;
import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserIdInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import com.br.devs.shared_restaurant.core.exceptions.RestaurantValidationException;
import com.br.devs.shared_restaurant.core.interfaces.ICuisineTypeUseCase;
import com.br.devs.shared_restaurant.core.interfaces.IRestaurantUseCase;
import com.br.devs.shared_restaurant.core.interfaces.IUserUseCase;
import com.br.devs.shared_restaurant.utils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RestaurantUseCaseIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private IRestaurantUseCase restaurantUseCase;

    @Autowired
    private ICuisineTypeUseCase cuisineTypeUseCase;

    @Autowired
    private IUserUseCase userUseCase;

    @Test
    void shouldCreateRestaurantSuccessfully() {
        CuisineTypeOutputDTO cuisineType = createTestCuisineType("Italian");
        UserOutputDTO owner = createTestOwner("owner@test.com");

        RestaurantInputDTO inputDTO = createRestaurantInputDTO("Pizzeria Bella Napoli", "08:00-22:00", cuisineType.getId(), owner.getId());

        RestaurantOutputDTO result = restaurantUseCase.create(inputDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("Pizzeria Bella Napoli");
        assertThat(result.getOpeningHours()).isEqualTo("08:00-22:00");
        assertThat(result.getCuisineType().getId()).isEqualTo(cuisineType.getId());
        assertThat(result.getOwner().getId()).isEqualTo(owner.getId());
        assertThat(result.getAddress()).isNotNull();
        assertThat(result.getAddress().getCity()).isEqualTo("Test City");
    }

    @Test
    void shouldFindRestaurantById() {
        CuisineTypeOutputDTO cuisineType = createTestCuisineType("Mexican");
        UserOutputDTO owner = createTestOwner("mexican@test.com");

        RestaurantInputDTO inputDTO = createRestaurantInputDTO("Taco Bell", "10:00-23:00", cuisineType.getId(), owner.getId());
        RestaurantOutputDTO createdRestaurant = restaurantUseCase.create(inputDTO);

        RestaurantOutputDTO foundRestaurant = restaurantUseCase.getById(createdRestaurant.getId());

        assertThat(foundRestaurant).isNotNull();
        assertThat(foundRestaurant.getId()).isEqualTo(createdRestaurant.getId());
        assertThat(foundRestaurant.getName()).isEqualTo("Taco Bell");
        assertThat(foundRestaurant.getOpeningHours()).isEqualTo("10:00-23:00");
        assertThat(foundRestaurant.getCuisineType().getName()).isEqualTo("Mexican");
        assertThat(foundRestaurant.getOwner().getMail()).isEqualTo("mexican@test.com");
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        CuisineTypeOutputDTO originalCuisineType = createTestCuisineType("Brazilian");
        CuisineTypeOutputDTO newCuisineType = createTestCuisineType("Japanese");
        UserOutputDTO originalOwner = createTestOwner("original@test.com");
        UserOutputDTO newOwner = createTestOwner("new@test.com");

        RestaurantInputDTO inputDTO = createRestaurantInputDTO("Churrascaria", "11:00-22:00", originalCuisineType.getId(), originalOwner.getId());
        RestaurantOutputDTO createdRestaurant = restaurantUseCase.create(inputDTO);

        RestaurantInputDTO updateDTO = createRestaurantInputDTO("Sushi Bar", "18:00-23:00", newCuisineType.getId(), newOwner.getId());
        RestaurantOutputDTO updatedRestaurant = restaurantUseCase.update(createdRestaurant.getId(), updateDTO);

        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getId()).isEqualTo(createdRestaurant.getId());
        assertThat(updatedRestaurant.getName()).isEqualTo("Sushi Bar");
        assertThat(updatedRestaurant.getOpeningHours()).isEqualTo("18:00-23:00");
        assertThat(updatedRestaurant.getCuisineType().getId()).isEqualTo(newCuisineType.getId());
        assertThat(updatedRestaurant.getOwner().getId()).isEqualTo(newOwner.getId());
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() {
        CuisineTypeOutputDTO cuisineType = createTestCuisineType("Thai");
        UserOutputDTO owner = createTestOwner("thai@test.com");

        RestaurantInputDTO inputDTO = createRestaurantInputDTO("Thai Garden", "12:00-21:00", cuisineType.getId(), owner.getId());
        RestaurantOutputDTO createdRestaurant = restaurantUseCase.create(inputDTO);

        restaurantUseCase.delete(createdRestaurant.getId());

        assertThatThrownBy(() -> restaurantUseCase.getById(createdRestaurant.getId()))
                .isInstanceOf(RestaurantValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        String nonExistentId = "non-existent-id";

        assertThatThrownBy(() -> restaurantUseCase.getById(nonExistentId))
                .isInstanceOf(RestaurantValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentRestaurant() {
        String nonExistentId = "non-existent-id";
        CuisineTypeOutputDTO cuisineType = createTestCuisineType("French");
        UserOutputDTO owner = createTestOwner("french@test.com");

        RestaurantInputDTO updateDTO = createRestaurantInputDTO("French Bistro", "17:00-22:00", cuisineType.getId(), owner.getId());

        assertThatThrownBy(() -> restaurantUseCase.update(nonExistentId, updateDTO))
                .isInstanceOf(RestaurantValidationException.class);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentRestaurant() {
        String nonExistentId = "non-existent-id";

        assertThatThrownBy(() -> restaurantUseCase.delete(nonExistentId))
                .isInstanceOf(RestaurantValidationException.class);
    }

    @Test
    void shouldCreateMultipleRestaurantsWithDifferentNames() {
        CuisineTypeOutputDTO italianCuisine = createTestCuisineType("Italian");
        CuisineTypeOutputDTO chineseCuisine = createTestCuisineType("Chinese");
        CuisineTypeOutputDTO indianCuisine = createTestCuisineType("Indian");

        UserOutputDTO owner1 = createTestOwner("owner1@test.com");
        UserOutputDTO owner2 = createTestOwner("owner2@test.com");
        UserOutputDTO owner3 = createTestOwner("owner3@test.com");

        RestaurantInputDTO inputDTO1 = createRestaurantInputDTO("Bella Vita", "11:00-22:00", italianCuisine.getId(), owner1.getId());
        RestaurantOutputDTO restaurant1 = restaurantUseCase.create(inputDTO1);

        RestaurantInputDTO inputDTO2 = createRestaurantInputDTO("Golden Dragon", "12:00-23:00", chineseCuisine.getId(), owner2.getId());
        RestaurantOutputDTO restaurant2 = restaurantUseCase.create(inputDTO2);

        RestaurantInputDTO inputDTO3 = createRestaurantInputDTO("Spice Palace", "10:00-21:00", indianCuisine.getId(), owner3.getId());
        RestaurantOutputDTO restaurant3 = restaurantUseCase.create(inputDTO3);

        assertThat(restaurant1.getName()).isEqualTo("Bella Vita");
        assertThat(restaurant2.getName()).isEqualTo("Golden Dragon");
        assertThat(restaurant3.getName()).isEqualTo("Spice Palace");

        assertThat(restaurant1.getId()).isNotEqualTo(restaurant2.getId());
        assertThat(restaurant2.getId()).isNotEqualTo(restaurant3.getId());
        assertThat(restaurant1.getId()).isNotEqualTo(restaurant3.getId());

        assertThat(restaurant1.getCuisineType().getName()).isEqualTo("Italian");
        assertThat(restaurant2.getCuisineType().getName()).isEqualTo("Chinese");
        assertThat(restaurant3.getCuisineType().getName()).isEqualTo("Indian");
    }

    @Test
    void shouldUpdateOnlyRestaurantNameAndOpeningHours() {
        CuisineTypeOutputDTO cuisineType = createTestCuisineType("Mediterranean");
        UserOutputDTO owner = createTestOwner("med@test.com");

        RestaurantInputDTO inputDTO = createRestaurantInputDTO("Mediterranean Delight", "09:00-20:00", cuisineType.getId(), owner.getId());
        RestaurantOutputDTO createdRestaurant = restaurantUseCase.create(inputDTO);

        RestaurantInputDTO updateDTO = createRestaurantInputDTO("Mediterranean Paradise", "08:00-22:00", cuisineType.getId(), owner.getId());
        RestaurantOutputDTO updatedRestaurant = restaurantUseCase.update(createdRestaurant.getId(), updateDTO);

        assertThat(updatedRestaurant.getName()).isEqualTo("Mediterranean Paradise");
        assertThat(updatedRestaurant.getOpeningHours()).isEqualTo("08:00-22:00");
        assertThat(updatedRestaurant.getCuisineType().getId()).isEqualTo(cuisineType.getId());
        assertThat(updatedRestaurant.getOwner().getId()).isEqualTo(owner.getId());
        assertThat(updatedRestaurant.getAddress().getCity()).isEqualTo("Test City");
    }

    private CuisineTypeOutputDTO createTestCuisineType(String name) {
        CuisineTypeInputDTO cuisineTypeInputDTO = new CuisineTypeInputDTO();
        cuisineTypeInputDTO.setName(name);
        return cuisineTypeUseCase.create(cuisineTypeInputDTO);
    }

    private UserOutputDTO createTestOwner(String email) {
        UserCreateDTO userCreateDTO = TestDataBuilder.defaultUser()
                .withMail(email)
                .withUserType(UserTypeEnum.OWNER)
                .buildCreateDTO();
        return userUseCase.createUser(userCreateDTO);
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
