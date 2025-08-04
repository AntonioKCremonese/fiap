package com.br.devs.shared_restaurant.utils;

import com.br.devs.shared_restaurant.core.dto.input.AddressInputDTO;
import com.br.devs.shared_restaurant.core.dto.input.PasswordUpdateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserCreateDTO;
import com.br.devs.shared_restaurant.core.dto.input.UserUpdateDTO;
import com.br.devs.shared_restaurant.core.entities.Address;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.entities.MenuItem;
import com.br.devs.shared_restaurant.core.entities.Restaurant;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;

import java.math.BigDecimal;

public class TestDataBuilder {

    public static class UserBuilder {
        private String name = "Test User";
        private String mail = "test@example.com";
        private String login = "testuser";
        private String password = "TestPass123";
        private UserTypeEnum userType = UserTypeEnum.CLIENT;
        private Address address;

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withMail(String mail) {
            this.mail = mail;
            this.login = mail.split("@")[0];
            return this;
        }

        public UserBuilder withLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withUserType(UserTypeEnum userType) {
            this.userType = userType;
            return this;
        }

        public UserBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public User build() {
            return User.builder()
                    .name(name)
                    .mail(mail)
                    .login(login)
                    .password(password)
                    .userType(userType)
                    .address(address != null ? address : defaultAddress().build())
                    .build();
        }

        public UserCreateDTO buildCreateDTO() {
            UserCreateDTO dto = new UserCreateDTO();
            dto.setName(name);
            dto.setMail(mail);
            dto.setLogin(login);
            dto.setPassword(password);
            dto.setPasswordConfirmation(password);
            dto.setUserType(userType);
            dto.setAddress(defaultAddressInputDTO().build());
            return dto;
        }

        public UserUpdateDTO buildUpdateDTO() {
            UserUpdateDTO dto = new UserUpdateDTO();
            dto.setName(name);
            dto.setMail(mail);
            dto.setLogin(login);
            dto.setUserType(userType);
            return dto;
        }
    }

    public static class PasswordUpdateDTOBuilder {
        private String newPassword = "NewPass123";
        private String passwordConfirmation = "NewPass123";
        private String currentPassword = "TestPass123";

        public PasswordUpdateDTOBuilder withNewPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public PasswordUpdateDTOBuilder withPasswordConfirmation(String passwordConfirmation) {
            this.passwordConfirmation = passwordConfirmation;
            return this;
        }

        public PasswordUpdateDTOBuilder withCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        public PasswordUpdateDTO build() {
            return new PasswordUpdateDTO(newPassword, passwordConfirmation, currentPassword);
        }
    }

    public static class AddressBuilder {
        private String street = "Test Street";
        private int number = 123;
        private String complement = "Apt 1";
        private String neighborhood = "Test Neighborhood";
        private String city = "Test City";
        private String state = "TS";
        private String country = "Brazil";
        private String postalCode = "12345678";
        private String reference = "Near test location";

        public AddressBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder withNumber(int number) {
            this.number = number;
            return this;
        }

        public AddressBuilder withComplement(String complement) {
            this.complement = complement;
            return this;
        }

        public AddressBuilder withNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public AddressBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public AddressBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Address build() {
            return Address.builder()
                    .street(street)
                    .number(number)
                    .complement(complement)
                    .neighborhood(neighborhood)
                    .city(city)
                    .state(state)
                    .country(country)
                    .postalCode(postalCode)
                    .reference(reference)
                    .build();
        }
    }

    public static class AddressInputDTOBuilder {
        private String street = "Test Street";
        private Integer number = 123;
        private String complement = "Apt 1";
        private String neighborhood = "Test Neighborhood";
        private String city = "Test City";
        private String state = "TS";
        private String country = "Brazil";
        private String postalCode = "12345678";
        private String reference = "Near test location";

        public AddressInputDTOBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressInputDTOBuilder withNumber(Integer number) {
            this.number = number;
            return this;
        }

        public AddressInputDTOBuilder withComplement(String complement) {
            this.complement = complement;
            return this;
        }

        public AddressInputDTOBuilder withNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public AddressInputDTOBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AddressInputDTOBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public AddressInputDTOBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public AddressInputDTO build() {
            AddressInputDTO dto = new AddressInputDTO();
            dto.setStreet(street);
            dto.setNumber(number);
            dto.setComplement(complement);
            dto.setNeighborhood(neighborhood);
            dto.setCity(city);
            dto.setState(state);
            dto.setCountry(country);
            dto.setPostalCode(postalCode);
            dto.setReference(reference);
            return dto;
        }
    }

    public static class CuisineTypeBuilder {
        private String name = "Test Cuisine";

        public CuisineTypeBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CuisineType build() {
            return CuisineType.builder()
                    .name(name)
                    .build();
        }
    }

    public static class RestaurantBuilder {
        private String name = "Test Restaurant";
        private String openingHours = "08:00-22:00";
        private CuisineType cuisineType;
        private Address address;
        private User owner;

        public RestaurantBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RestaurantBuilder withOpeningHours(String openingHours) {
            this.openingHours = openingHours;
            return this;
        }

        public RestaurantBuilder withCuisineType(CuisineType cuisineType) {
            this.cuisineType = cuisineType;
            return this;
        }

        public RestaurantBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public RestaurantBuilder withOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Restaurant build() {
            return Restaurant.builder()
                    .name(name)
                    .openingHours(openingHours)
                    .cuisineType(cuisineType != null ? cuisineType : defaultCuisineType().build())
                    .address(address != null ? address : defaultAddress().build())
                    .owner(owner != null ? owner : defaultUser().withUserType(UserTypeEnum.OWNER).build())
                    .build();
        }
    }

    public static class MenuItemBuilder {
        private String name = "Test Menu Item";
        private String description = "Test menu item description";
        private BigDecimal price = new BigDecimal("29.99");
        private boolean availableForDineInOnly = false;
        private String photoPath = "/images/test.jpg";
        private Restaurant restaurant;

        public MenuItemBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MenuItemBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MenuItemBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MenuItemBuilder withAvailableForDineInOnly(boolean availableForDineInOnly) {
            this.availableForDineInOnly = availableForDineInOnly;
            return this;
        }

        public MenuItemBuilder withRestaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public MenuItem build() {
            return MenuItem.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .availableForDineInOnly(availableForDineInOnly)
                    .photoPath(photoPath)
                    .restaurant(restaurant != null ? restaurant : defaultRestaurant().build())
                    .build();
        }
    }

    public static class MenuItemInputDTOBuilder {
        private String name = "Test Menu Item";
        private String description = "Test menu item description";
        private BigDecimal price = new BigDecimal("29.99");
        private boolean availableForDineInOnly = false;
        private String photoPath = "/images/test.jpg";
        private String restaurantId;

        public MenuItemInputDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MenuItemInputDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MenuItemInputDTOBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MenuItemInputDTOBuilder withAvailableForDineInOnly(boolean availableForDineInOnly) {
            this.availableForDineInOnly = availableForDineInOnly;
            return this;
        }

        public MenuItemInputDTOBuilder withPhotoPath(String photoPath) {
            this.photoPath = photoPath;
            return this;
        }

        public MenuItemInputDTOBuilder withRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO build() {
            com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO dto = new com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO();
            dto.setName(name);
            dto.setDescription(description);
            dto.setPrice(price);
            dto.setAvailableForDineInOnly(availableForDineInOnly);
            dto.setPhotoPath(photoPath);

            if (restaurantId != null) {
                com.br.devs.shared_restaurant.core.dto.input.RestaurantIdInputDTO restaurantIdDTO = new com.br.devs.shared_restaurant.core.dto.input.RestaurantIdInputDTO();
                restaurantIdDTO.setId(restaurantId);
                dto.setRestaurant(restaurantIdDTO);
            }

            return dto;
        }
    }

    public static UserBuilder defaultUser() {
        return new UserBuilder();
    }

    public static AddressBuilder defaultAddress() {
        return new AddressBuilder();
    }

    public static AddressInputDTOBuilder defaultAddressInputDTO() {
        return new AddressInputDTOBuilder();
    }

    public static PasswordUpdateDTOBuilder defaultPasswordUpdateDTO() {
        return new PasswordUpdateDTOBuilder();
    }

    public static CuisineTypeBuilder defaultCuisineType() {
        return new CuisineTypeBuilder();
    }

    public static RestaurantBuilder defaultRestaurant() {
        return new RestaurantBuilder();
    }

    public static MenuItemBuilder defaultMenuItem() {
        return new MenuItemBuilder();
    }

    public static MenuItemInputDTOBuilder defaultMenuItemInputDTO() {
        return new MenuItemInputDTOBuilder();
    }
}