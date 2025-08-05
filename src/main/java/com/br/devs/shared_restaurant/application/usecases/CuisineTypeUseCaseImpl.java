package com.br.devs.shared_restaurant.application.usecases;

import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.entities.CuisineType;
import com.br.devs.shared_restaurant.core.gateways.ICuisineTypeGateway;
import com.br.devs.shared_restaurant.core.interfaces.ICuisineTypeUseCase;
import com.br.devs.shared_restaurant.core.presenters.CuisinePresenter;
import com.br.devs.shared_restaurant.core.exceptions.CuisineTypeValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuisineTypeUseCaseImpl implements ICuisineTypeUseCase {

    private final ICuisineTypeGateway cuisineTypeGateway;

    CuisineTypeUseCaseImpl(ICuisineTypeGateway cuisineTypeGateway) {
        this.cuisineTypeGateway = cuisineTypeGateway;
    }

    @Override
    @Transactional
    public CuisineTypeOutputDTO getById(String id) {
        CuisineType cuisineType = findById(id);
        return CuisinePresenter.toDTO(cuisineType);
    }

    @Override
    @Transactional
    public CuisineTypeOutputDTO create(CuisineTypeInputDTO cuisineTypeInputDTO) {
        cuisineTypeGateway.findCuisineTypeByName(cuisineTypeInputDTO.getName()).ifPresent(cuisineType -> CuisineType.validateNameAlreadyExists(cuisineType.getName(), cuisineTypeInputDTO.getName()));
        CuisineType cuisineType = CuisinePresenter.toEntity(cuisineTypeInputDTO);
        return CuisinePresenter.toDTO(cuisineTypeGateway.save(cuisineType));
    }

    @Override
    @Transactional
    public CuisineTypeOutputDTO update(String id, CuisineTypeInputDTO cuisineTypeInputDTO) {
        CuisineType existingCuisineType = findById(id);
        CuisineType.update(existingCuisineType, cuisineTypeInputDTO);
        return CuisinePresenter.toDTO(cuisineTypeGateway.save(existingCuisineType));
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            cuisineTypeGateway.deleteCuisineTypeById(findById(id).getId());
        } catch (DataIntegrityViolationException ex) {
            throw CuisineTypeValidationException.cuisineTypeInUseException();
        }
    }

    private CuisineType findById(String id) {
        return cuisineTypeGateway.findCuisineTypeById(id);
    }
}