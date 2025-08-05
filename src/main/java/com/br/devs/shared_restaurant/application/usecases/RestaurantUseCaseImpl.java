package com.br.devs.shared_restaurant.application.usecases;

import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.core.entities.Restaurant;
import com.br.devs.shared_restaurant.core.exceptions.RestaurantValidationException;
import com.br.devs.shared_restaurant.core.gateways.ICuisineTypeGateway;
import com.br.devs.shared_restaurant.core.gateways.IRestaurantGateway;
import com.br.devs.shared_restaurant.core.gateways.IUserGateway;
import com.br.devs.shared_restaurant.core.interfaces.IRestaurantUseCase;
import com.br.devs.shared_restaurant.core.presenters.RestaurantPresenter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantUseCaseImpl implements IRestaurantUseCase {
    private final ICuisineTypeGateway cuisineTypeGateway;
    private final IUserGateway userGateway;
    private final IRestaurantGateway restaurantGateway;

    RestaurantUseCaseImpl(ICuisineTypeGateway cuisineTypeGateway, IUserGateway userGateway, IRestaurantGateway restaurantGateway) {
        this.cuisineTypeGateway = cuisineTypeGateway;
        this.userGateway = userGateway;
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    @Transactional
    public RestaurantOutputDTO create(RestaurantInputDTO input) {
        restaurantGateway.findRestaurantByName(input.getName()).ifPresent(restaurant -> Restaurant.validateNameAlreadyExists(restaurant.getName(), input.getName()));
        Restaurant restaurant = RestaurantPresenter.toEntity(input);
        restaurant.setCuisineType(cuisineTypeGateway.findCuisineTypeById(input.getCuisineType().getId()));
        restaurant.setOwner(userGateway.findUserById(input.getOwner().getId()));
        return RestaurantPresenter.toDTO(restaurantGateway.save(restaurant));
    }

    @Override
    @Transactional
    public RestaurantOutputDTO getById(String id) {
        return RestaurantPresenter.toDTO(findById(id));
    }

    @Override
    @Transactional
    public RestaurantOutputDTO update(String id, RestaurantInputDTO restaurantInputDTO) {
        Restaurant existingRestaurant = findById(id);
        Restaurant.update(existingRestaurant, restaurantInputDTO);
        existingRestaurant.setCuisineType(cuisineTypeGateway.findCuisineTypeById(restaurantInputDTO.getCuisineType().getId()));
        existingRestaurant.setOwner(userGateway.findUserById(restaurantInputDTO.getOwner().getId()));
        return RestaurantPresenter.toDTO(restaurantGateway.save(existingRestaurant));
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            restaurantGateway.deleteRestaurantById(findById(id).getId());
        } catch (DataIntegrityViolationException ex) {
            throw RestaurantValidationException.restaurantInUseException();
        }
    }

    private Restaurant findById(String id) {
        return restaurantGateway.findRestaurantById(id);
    }
}