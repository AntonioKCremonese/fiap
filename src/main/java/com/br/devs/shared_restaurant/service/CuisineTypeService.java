package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.exception.CuisineTypeValidationException;
import com.br.devs.shared_restaurant.mapper.GenericMapper;
import com.br.devs.shared_restaurant.model.CuisineType;
import com.br.devs.shared_restaurant.repository.CuisineTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuisineTypeService {

    private final CuisineTypeRepository cuisineTypeRepository;

    private final GenericMapper<CuisineType, CuisineTypeInputDTO, CuisineTypeOutputDTO> mapper;

    public CuisineTypeService(CuisineTypeRepository cuisineTypeRepository,
                              GenericMapper<CuisineType, CuisineTypeInputDTO, CuisineTypeOutputDTO> mapper) {
        this.cuisineTypeRepository = cuisineTypeRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public CuisineTypeOutputDTO getById(String id) {
        CuisineType cuisineType = findById(id);
        return mapper.fromEntity(cuisineType, CuisineTypeOutputDTO.class);
    }

    @Transactional
    public CuisineTypeOutputDTO create(CuisineTypeInputDTO cuisineTypeInputDTO) {
        CuisineType cuisineType = mapper.toEntity(cuisineTypeInputDTO, CuisineType.class);
        cuisineType = cuisineTypeRepository.save(cuisineType);
        return mapper.fromEntity(cuisineType, CuisineTypeOutputDTO.class);
    }

    @Transactional
    public CuisineTypeOutputDTO update(String id, CuisineTypeInputDTO cuisineTypeInputDTO) {
        CuisineType existingCuisineType = findById(id);
        mapper.updateEntity(cuisineTypeInputDTO, existingCuisineType);
        existingCuisineType = cuisineTypeRepository.save(existingCuisineType);
        return mapper.fromEntity(existingCuisineType, CuisineTypeOutputDTO.class);
    }

    @Transactional
    public void delete(String id) {
        try {
            cuisineTypeRepository.delete(findById(id));
            cuisineTypeRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw CuisineTypeValidationException.cuisineTypeInUseException();
        }
    }

    protected CuisineType findById(String id) {
        return cuisineTypeRepository.findById(id)
                .orElseThrow(CuisineTypeValidationException::cuisineTypeNotFoundException);
    }
}