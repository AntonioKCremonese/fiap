package com.br.devs.shared_restaurant.infrastructure.gateways;

import com.br.devs.shared_restaurant.application.mapper.GenericMapper;
import com.br.devs.shared_restaurant.core.entities.Restaurant;
import com.br.devs.shared_restaurant.core.gateways.IRestaurantGateway;
import com.br.devs.shared_restaurant.infrastructure.model.CuisineTypeEntity;
import com.br.devs.shared_restaurant.infrastructure.model.RestaurantEntity;
import com.br.devs.shared_restaurant.infrastructure.model.UserEntity;
import com.br.devs.shared_restaurant.infrastructure.repository.RestaurantRepository;
import com.br.devs.shared_restaurant.core.exceptions.RestaurantValidationException;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RestaurantGatewayImpl implements IRestaurantGateway {
    private final RestaurantRepository restaurantRepository;
    private final GenericMapper<RestaurantEntity, Restaurant, Restaurant> mapper;
    private final EntityManager entityManager;

    public RestaurantGatewayImpl(RestaurantRepository restaurantRepository,
                                 GenericMapper<RestaurantEntity, Restaurant, Restaurant> mapper,
                                 EntityManager entityManager) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        var restaurantToSave = mapper.toEntity(restaurant, RestaurantEntity.class);

        UserEntity ownerRef = entityManager.getReference(UserEntity.class, restaurant.getOwner().getId());
        CuisineTypeEntity cuisineTypeRef = entityManager.getReference(CuisineTypeEntity.class, restaurant.getCuisineType().getId());

        restaurantToSave.setOwner(ownerRef);
        restaurantToSave.setCuisineType(cuisineTypeRef);

        var saved = restaurantRepository.save(restaurantToSave);
        return mapper.fromEntity(saved, Restaurant.class);
    }

    @Override
    public Restaurant findRestaurantById(String id) {
        return mapper.fromEntity(restaurantRepository.findById(id).orElseThrow(RestaurantValidationException::restaurantNotFoundException), Restaurant.class);
    }

    @Override
    public Optional<Restaurant> findRestaurantByName(String name) {
        return restaurantRepository.findByName(name)
                .map(entity -> mapper.fromEntity(entity, Restaurant.class));
    }

    @Override
    public void deleteRestaurantById(String id) {
        restaurantRepository.deleteById(id);
        restaurantRepository.flush();
    }
}
