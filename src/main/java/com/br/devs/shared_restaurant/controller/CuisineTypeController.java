package com.br.devs.shared_restaurant.controller;

import com.br.devs.shared_restaurant.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.service.CuisineTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cuisine-types")
public class CuisineTypeController {

    private final CuisineTypeService cuisineTypeService;

    public CuisineTypeController(CuisineTypeService cuisineTypeService) {
        this.cuisineTypeService = cuisineTypeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineTypeOutputDTO create(@RequestBody @Valid CuisineTypeInputDTO input) {
        return cuisineTypeService.create(input);
    }

    @PutMapping("/{id}")
    public CuisineTypeOutputDTO update(@PathVariable String id, @RequestBody @Valid CuisineTypeInputDTO input) {
        return cuisineTypeService.update(id, input);
    }

    @GetMapping("/{id}")
    public CuisineTypeOutputDTO getById(@PathVariable String id) {
        return cuisineTypeService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        cuisineTypeService.delete(id);
    }
}