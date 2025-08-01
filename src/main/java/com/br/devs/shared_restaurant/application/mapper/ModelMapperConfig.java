package com.br.devs.shared_restaurant.application.mapper;

import com.br.devs.shared_restaurant.core.entities.Restaurant;
import com.br.devs.shared_restaurant.infrastructure.model.RestaurantEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        TypeMap<Restaurant, RestaurantEntity> restaurantMap = modelMapper.createTypeMap(Restaurant.class, RestaurantEntity.class);
        restaurantMap.addMappings(mapper -> {
            mapper.skip(RestaurantEntity::setOwner);
            mapper.skip(RestaurantEntity::setCuisineType);
        });
        return modelMapper;
    }
}