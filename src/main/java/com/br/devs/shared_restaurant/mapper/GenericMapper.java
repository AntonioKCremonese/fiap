package com.br.devs.shared_restaurant.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenericMapper<E, I, O> {

    private final ModelMapper modelMapper;

    public GenericMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public O fromEntity(E entity, Class<O> outputDtoClass) {
        return modelMapper.map(entity, outputDtoClass);
    }

    public E toEntity(I inputDto, Class<E> entityClass) {
        return modelMapper.map(inputDto, entityClass);
    }

    public void updateEntity(I inputDto, E entity) {
        modelMapper.map(inputDto, entity);
    }
}