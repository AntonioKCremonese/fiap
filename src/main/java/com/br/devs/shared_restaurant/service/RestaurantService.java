package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.exception.RestaurantValidationException;
import com.br.devs.shared_restaurant.mapper.GenericMapper;
import com.br.devs.shared_restaurant.model.Restaurant;
import com.br.devs.shared_restaurant.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final GenericMapper<Restaurant, RestaurantInputDTO, RestaurantOutputDTO> mapper;
    private final CuisineTypeService cuisineTypeService;
    private final UserService userService;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             GenericMapper<Restaurant, RestaurantInputDTO, RestaurantOutputDTO> mapper,
                             CuisineTypeService cuisineTypeService,
                             UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
        this.cuisineTypeService = cuisineTypeService;
        this.userService = userService;
    }

    @Transactional
    public RestaurantOutputDTO create(RestaurantInputDTO restaurantInputDTO) {
        Restaurant restaurant = mapper.toEntity(restaurantInputDTO, Restaurant.class);
        restaurant.setCuisineType(cuisineTypeService.findById(restaurantInputDTO.getCuisineType().getId()));
        restaurant.setOwner(userService.findById(restaurantInputDTO.getOwner().getId()));
        restaurant = restaurantRepository.save(restaurant);
        return mapper.fromEntity(restaurant, RestaurantOutputDTO.class);
    }

    @Transactional(readOnly = true)
    public RestaurantOutputDTO getById(String id) {
        Restaurant restaurant = findById(id);
        return mapper.fromEntity(restaurant, RestaurantOutputDTO.class);
    }

    @Transactional
    public RestaurantOutputDTO update(String id, RestaurantInputDTO restaurantInputDTO) {
        Restaurant existingRestaurant = findById(id);
        mapper.updateEntity(restaurantInputDTO, existingRestaurant);
        existingRestaurant.setCuisineType(cuisineTypeService.findById(restaurantInputDTO.getCuisineType().getId()));
        existingRestaurant.setOwner(userService.findById(restaurantInputDTO.getOwner().getId()));
        existingRestaurant = restaurantRepository.save(existingRestaurant);
        return mapper.fromEntity(existingRestaurant, RestaurantOutputDTO.class);
    }

    @Transactional
    public void delete(String id) {
        try {
            restaurantRepository.delete(findById(id));
            restaurantRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw RestaurantValidationException.restaurantInUseException();
        }
    }

    protected Restaurant findById(String id) {
        return restaurantRepository.findById(id)
                .orElseThrow(RestaurantValidationException::restaurantNotFoundException);
    }
}