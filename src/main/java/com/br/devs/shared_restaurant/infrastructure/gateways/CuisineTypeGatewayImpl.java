package com.br.devs.shared_restaurant.infrastructure.gateways;

import com.br.devs.shared_restaurant.application.mapper.GenericMapper;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.gateways.ICuisineTypeGateway;
import com.br.devs.shared_restaurant.infrastructure.model.CuisineTypeEntity;
import com.br.devs.shared_restaurant.infrastructure.repository.CuisineTypeRepository;
import com.br.devs.shared_restaurant.core.exceptions.CuisineTypeValidationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CuisineTypeGatewayImpl implements ICuisineTypeGateway {

    private final CuisineTypeRepository cuisineTypeRepository;
    private final GenericMapper<CuisineTypeEntity, CuisineType, CuisineType> mapper;

    CuisineTypeGatewayImpl(CuisineTypeRepository cuisineTypeRepository, GenericMapper<CuisineTypeEntity, CuisineType, CuisineType> mapper) {
        this.cuisineTypeRepository = cuisineTypeRepository;
        this.mapper = mapper;
    }

    @Override
    public CuisineType findCuisineTypeById(String id) {
        return mapper.fromEntity(cuisineTypeRepository.findById(id).orElseThrow(CuisineTypeValidationException::cuisineTypeNotFoundException), CuisineType.class);
    }

    @Override
    public Optional<CuisineType> findCuisineTypeByName(String name) {
        return cuisineTypeRepository.findByName(name)
                .map(entity -> mapper.fromEntity(entity, CuisineType.class));
    }

    @Override
    public CuisineType save(CuisineType cuisineType) {
        CuisineTypeEntity cuisineTypeSaved = cuisineTypeRepository.save(mapper.toEntity(cuisineType, CuisineTypeEntity.class));
        return mapper.fromEntity(cuisineTypeSaved, CuisineType.class);
    }

    @Override
    public void deleteCuisineTypeById(String id) {
        cuisineTypeRepository.deleteById(id);
        cuisineTypeRepository.flush();
    }
}
